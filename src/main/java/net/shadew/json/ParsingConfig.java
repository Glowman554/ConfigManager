package net.shadew.json;

public class ParsingConfig {
    private boolean json5;
    private boolean anyValue;
    private boolean allowNonExecutePrefix;

    private ParsingConfig() {
    }

    public static ParsingConfig standard() {
        return new ParsingConfig();
    }

    public boolean json5() {
        return json5;
    }

    public ParsingConfig json5(boolean json5) {
        this.json5 = json5;
        return this;
    }

    public boolean anyValue() {
        return anyValue;
    }

    public ParsingConfig anyValue(boolean anyValue) {
        this.anyValue = anyValue;
        return this;
    }

    public boolean allowNonExecutePrefix() {
        return allowNonExecutePrefix;
    }

    public ParsingConfig allowNonExecutePrefix(boolean allowNonExecutePrefix) {
        this.allowNonExecutePrefix = allowNonExecutePrefix;
        return this;
    }

    public ParsingConfig copy() {
        return new ParsingConfig().copyFrom(this);
    }

    public ParsingConfig copyFrom(ParsingConfig copy) {
        this.json5 = copy.json5;
        this.anyValue = copy.anyValue;
        this.allowNonExecutePrefix = copy.allowNonExecutePrefix;
        return this;
    }
}
