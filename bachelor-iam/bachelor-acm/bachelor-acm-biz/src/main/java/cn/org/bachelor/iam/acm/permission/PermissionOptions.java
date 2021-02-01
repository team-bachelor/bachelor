package cn.org.bachelor.iam.acm.permission;

public class PermissionOptions {
    public enum AccessType {
        INTERFACE("i"), RESOURCE("r");

        private final String type;

        AccessType(String t) {
            this.type = t;
        }

        public String toString() {
            return type;
        }
    }

    public enum CheckLevel {
        Authorized("c"), LOGON("l"), NONE("a");

        private final String type;

        CheckLevel(String l) {
            this.type = l;
        }
    }
}

