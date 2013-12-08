
public class Hashtag implements Comparable{
    public Hashtag(String tag){
        this.occurrency =1;
        this.tag = tag;
    }
    private String tag;
    private int occurrency;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getOccurrency() {
        return occurrency;
    }

    public void setOccurrency(int occurrency) {
        this.occurrency = occurrency;
    }


    @Override
    public int compareTo(Object t) {
        Hashtag ht = (Hashtag)t;
        if (this.getOccurrency()>ht.getOccurrency()){
            return -1;
        }else if (this.getOccurrency()<ht.getOccurrency()){
            return 1;
        }else{
            return 0;
        }
    }
    
    @Override
    public String toString(){
        return this.getTag()+":"+getOccurrency();
    }
}
