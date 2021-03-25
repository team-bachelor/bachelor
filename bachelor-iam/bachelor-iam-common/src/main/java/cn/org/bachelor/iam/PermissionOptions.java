package cn.org.bachelor.iam;

public class PermissionOptions {
    public enum Type {
        INTERFACE("i"), RESOURCE("r");

        private final String type;

        Type(String t) {
            this.type = t;
        }

        public String toString() { return type; }
    }

    public enum CheckLevel {
        Authorized("c"), LOGON("l"), NONE("a");

        private final String type;

        CheckLevel(String l) {
            this.type = l;
        }
        public String toString() {
            return this.type;
        }
    }
}

