package com.example.network.autocloseable;

public class ResourceCloseMainV4 {
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

        try (ResourceV2 resourceV1 = new ResourceV2("resource1");
             ResourceV2 resourceV2 = new ResourceV2("resource2")) {

            resourceV1.call();

            //자원을 닫을때 생기는 에러(서브에러) 는 이 메인에러 안으로 들어간다!!
            //그래서 자원닫을때 에러 closeException이 아니라 callException이 발생한다
            resourceV2.callEx();
        } catch (CallException e) {
            System.out.println("ex:" + e);
            throw e;
        }


    }
}
