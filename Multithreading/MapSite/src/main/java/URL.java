import java.util.concurrent.CopyOnWriteArraySet;

public class URL implements Comparable<URL>{

    private volatile URL parentUrl;
    private volatile int level;
    private String url;
    private volatile CopyOnWriteArraySet<URL> childUrl;

    public URL(String url) {
        level = 0;
        this.url = url;
        parentUrl = null;
        childUrl = new CopyOnWriteArraySet<>();
    }

    private int initLevel() {
        int result = 0;
        if (parentUrl == null) {
            return result;
        }
        result = 1 + parentUrl.initLevel();
        return result;
    }

    public int getLevel() {
        return level;
    }

    public synchronized void addChildUrl(URL url) {
        URL link = getLink();
        if(!link.contains(url.getUrl())) {
            url.setParentUrl(this);
            childUrl.add(url);
        }
    }

    private boolean contains(String url) {
        if (this.url.equals(url)) {
            return true;
        }
        for (URL child : childUrl) {
            if(child.contains(url))
                return true;
        }
        return false;
    }

    public String getUrl() {
        return url;
    }

    private synchronized void setParentUrl(URL parentUrl) {
            this.parentUrl = parentUrl;
            this.level = initLevel();
    }

    public URL getLink() {
        return parentUrl == null ? this : parentUrl.getLink();
    }

    public CopyOnWriteArraySet<URL> getChildUrl() {
        return childUrl;
    }

    @Override
    public int compareTo(URL o) {
        return url.compareTo(o.getUrl());
    }

    @Override
    public String toString() {
        return "URL{" +
                ", level=" + level +
                ", url='" + url + '\'' +
                ", childUrl=" + childUrl +
                '}';
    }
}
