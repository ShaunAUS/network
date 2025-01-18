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

        //ResourceV2는 AutoCloseable을 구현하고 있기 때문에 try-with-resources 사용 가능, 자동으로 자원 닫힘
        //!!순서 역순으로 닫히고, 메인예외 안에 서브 예외숨김 처리까지 자동!!
        try (ResourceV2 resource1 = new ResourceV2("resource1");
             ResourceV2 resource2 = new ResourceV2("resource2")) {

            resource1.call();
            resource2.callEx(); // 여기서 예외터치면 -> 자원정리 -> catch순으로 실행됨!

            //자원을 닫을때 생기는 에러(서브에러) 는 이 메인에러 안으로 들어간다!!
            //그래서 자원닫을때 에러 closeException이 아니라 callException이 발생한다
            //resource2.close(); AutoCloseable로  자동으로 처리
            //resource1.close();  AutoCloseable로  자동으로 처리
        } catch (CallException e) {
            System.out.println("ex:" + e);
            throw e;
        }


    }
}
