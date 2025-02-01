package com.tester;

public class EnumTester {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
    
    public static enum UserRankingJobState {
        COUNT_EVENT(null) {
            @Override
            public void cleanup(String message) {
                super.cleanup(message);
            }
        };
        public void cleanup(String message) {
            
        }
        private UserRankingJobState(String root) {
        }
    }
}
