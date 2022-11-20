package Util.CommandLine;

public class CMDOption {
    private String name;
    private boolean required;
    private String description;
    private boolean argumentRequired;

    public CMDOption(String name, boolean required, String description, boolean argumentRequred) {
        this.name = name;
        this.required = required;
        this.description = description;
        this.argumentRequired = argumentRequred;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isArgumentRequired() {
        return argumentRequired;
    }

    public void setArgumentRequired(boolean argumentRequired) {
        this.argumentRequired = argumentRequired;
    }

    @Override
    public String toString() {
        return  "-" + name+
                (required?"(true)":"(false)") + (argumentRequired?" <arg>":"") +"\t"+
                description+
                "\n";
    }
}
