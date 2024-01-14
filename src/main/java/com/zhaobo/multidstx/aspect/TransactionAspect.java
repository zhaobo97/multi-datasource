package com.zhaobo.multidstx.aspect;

import com.zhaobo.multidstx.annotation.MultiTransactional;
import javafx.util.Pair;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;

@Aspect
@Component
public class TransactionAspect {
    private static final ThreadLocal<Deque<Pair<DataSourceTransactionManager, TransactionStatus>>> STACK_THREAD_LOCAL = new ThreadLocal<>();

    @Resource
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.zhaobo.multidstx.annotation.MultiTransactional)")
    public void txPointCut(){}


    /**
     * 声明事务
     * @param transactional
     */
    @Before(value = "txPointCut() && @annotation(transactional)")
    public void before(MultiTransactional transactional){
        // 根据设置的事务名称按顺序声明，并放到ThreadLocal里
        // 创建栈
        Deque<Pair<DataSourceTransactionManager, TransactionStatus>> stack = new LinkedList<>();
        // 先获取所有的txManager的name
        String[] transactionManagerNames = transactional.transactionManagers();
        for (String transactionManagerName : transactionManagerNames) {
            // 获取bean，并创建BeanDefinition
            DataSourceTransactionManager transactionManager = applicationContext
                    .getBean(transactionManagerName, DataSourceTransactionManager.class);
            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            // 设置非只读、隔离级别、传播行为
            definition.setReadOnly(false);
            definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
            definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            // 获取事务状态
            TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
            // 入栈
            stack.push(new Pair<>(transactionManager, transactionStatus));
        }
        // 加入到LoaclThread
        STACK_THREAD_LOCAL.set(stack);
    }

    /**
     * 提交事务
     */
    @AfterReturning(value = "txPointCut()")
    public void afterReturning(){
        // 弹栈，事务后进先出
        Deque<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = STACK_THREAD_LOCAL.get();
        // 从pair中取出 全部提交
        while (!pairStack.isEmpty()){
            Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            TransactionStatus transactionStatus = pair.getValue();
            DataSourceTransactionManager transactionManager = pair.getKey();
            transactionManager.commit(transactionStatus);
        }
        // 清空本地变量
        STACK_THREAD_LOCAL.remove();
    }

    @AfterThrowing(value = "txPointCut()")
    public void afterThrowing(){
        Deque<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = STACK_THREAD_LOCAL.get();
        // 全部弹栈
        while (!pairStack.isEmpty()){
            // 声明事务和提交事务或者回滚事务的顺序应该相反的，就是先进后出
            Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            DataSourceTransactionManager transactionManager = pair.getKey();
            TransactionStatus transactionStatus = pair.getValue();
            transactionManager.rollback(transactionStatus);
        }
        STACK_THREAD_LOCAL.remove();
    }
}
