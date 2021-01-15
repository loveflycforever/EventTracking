package com.apoem.mmxx.eventtracking.infrastructure.dao.mongo;

class Test {
    public static void main(String[] args) {
        endTag:
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (i == 72) {
                    break endTag;
                }
            }
        }
    }
}