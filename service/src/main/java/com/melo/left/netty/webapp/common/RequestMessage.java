package com.melo.left.netty.webapp.common;

public class RequestMessage extends Message<Operation> {

    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationClazz();
    }

    public RequestMessage() {
    }

    public RequestMessage(Long streamId, Operation operation) {
        MessageHeader header = new MessageHeader();
        header.setStreamId(streamId);
        header.setOpCode(OperationType.fromOperation(operation).getOpCode());
        this.setMessageHeader(header);
        this.setMessageBody(operation);
    }
}
