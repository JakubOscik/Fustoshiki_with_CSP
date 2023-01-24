import java.util.List;

public class Variable {
    List<String> domain;
    String value;

    public Variable(List<String> domain, String value) {
        this.domain = domain;
        this.value = value;
    }

    public List<String> getDomain() {
        return domain;
    }

    public void setDomain(List<String> domain) {
        this.domain = domain;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void changeDomainGreater(String num){
        for(String s: this.domain){
            if(Integer.parseInt(s) < Integer.parseInt(num)) this.domain.remove(num);
        }
    }

    public void changeDomainLess(String num){
        for(String s: this.domain){
            if(Integer.parseInt(s) > Integer.parseInt(num)) this.domain.remove(num);
        }
    }

    public void deleteVal(String val) {
        this.domain.remove(val);
    }

    @Override
    public String toString() {
        return value;
    }
}
