package aashna.com.aashna.H_AskQuestion;


public class FAQ_pojo {

    String name;
    String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    String question;

    public FAQ_pojo(String name, String category, String question) {
        this.name = name;
        this.category = category;
        this.question = question;
    }


}
