package com.example.network.autocloseable;

public class ResourceCloseMainV3 {
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
        ResourceV1 resourceV1 = null;
        ResourceV1 resourceV2 = null;

        try {
            resourceV1 = new ResourceV1("ResourceV1");
            resourceV2 = new ResourceV1("ResourceV1");

            resourceV1.call();
            resourceV2.callEx();
        } catch (CallException e) {
            System.out.println("CallException 예외처리");
            throw new RuntimeException(e);
        } finally {
            System.out.println("자원정리");
            if (resourceV2 != null) {
                try {
                    resourceV2.closeEx();
                } catch (CloseException e) {
                    System.out.println("CloseException 예외처리");
                    throw new RuntimeException(e);
                }
            }
            if (resourceV1 != null) {
                try {
                    resourceV1.closeEx();
                } catch (CloseException e) {
                    System.out.println("CloseException 예외처리");
                    throw new RuntimeException(e);
                }
            }

        }


    }
}
