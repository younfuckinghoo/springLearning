package com.hy.consumer.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Slf4j
@Activate(group = CommonConstants.CONSUMER)
public class HyDubboConsumerTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
log.info("HyDubboConsumerTraceFilter----------------------");
        Result result = invoker.invoke(invocation);
        return result;
    }
}
