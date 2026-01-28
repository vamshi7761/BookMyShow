package com.bookmyshow.main.dto;

public class OutputCommand {
    private Object outputData;

    public OutputCommand() {}

    public OutputCommand(Object outputData) {
        this.outputData = outputData;
    }

    public Object getOutputData() {
        return outputData;
    }

    public void setOutputData(Object outputData) {
        this.outputData = outputData;
    }

    public static OutputCommandBuilder builder() {
        return new OutputCommandBuilder();
    }

    public static class OutputCommandBuilder {
        private Object outputData;

        public OutputCommandBuilder outputData(Object outputData) {
            this.outputData = outputData;
            return this;
        }

        public OutputCommand build() {
            return new OutputCommand(outputData);
        }
    }
}