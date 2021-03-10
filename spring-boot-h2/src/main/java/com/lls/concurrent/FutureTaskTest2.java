package com.lls.concurrent;



import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTaskTest2 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<FutureTask<String>> fts = new LinkedList<FutureTask<String>>();
        for(int i = 1; i < 10;i++){
           fts.add(new FutureTask<>(new RealData("data"+i,i*1000)));
        }

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        fts.forEach((f)->executorService.execute(f));

        System.out.println("ready...");
        Thread.sleep(2000);
        System.out.println("use 2 s");
            for(int k = 8;k > 0; k--){
            try {
                String result = fts.get(k).get();
                System.out.println("result:"+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        };

        long end = System.currentTimeMillis();

        long seed = end - start;
        System.out.println("seed=" + seed);
        executorService.shutdown();
    }

    static class RealData implements Callable {

        private String result ;

        private int sleepSeconds;

        public RealData(String result,int sleepSeconds) {
            this.result = result;
            this.sleepSeconds = sleepSeconds;
        }


        @Override
        public String call() throws Exception {
            StringBuffer sb = new StringBuffer();
            sb.append(result);
            Thread.sleep(sleepSeconds);
            // mock get data
            return sb.toString();
        }
    }
}



