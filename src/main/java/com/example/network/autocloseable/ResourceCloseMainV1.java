package com.example.network.autocloseable;

public class ResourceCloseMainV1 {
    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("CallException 예외처리");
            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("CloseException 예외처리");
            throw new RuntimeException(e);
        }

    }

    private static void logic() throws CloseException, CallException {
        ResourceV1 resourceV1 = new ResourceV1("ResourceV1");
        ResourceV1 resourceV2 = new ResourceV1("ResourceV1");

        resourceV1.call();
        resourceV2.callEx();

        System.out.println("자원정리");
        resourceV2.closeEx();
        resourceV1.closeEx();


    }
}
