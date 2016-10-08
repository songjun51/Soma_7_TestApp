package kr.wonjun.somatest;

/**
 * Created by swj85 on 2016-10-09.
 */

public class CustomVo {
    private String left, right, center, down, up, leftup, leftdown, rightup, rightdown;
    private int status;

    public CustomVo(int status, String left, String right, String center, String down, String up, String leftup, String leftdown, String rightup, String rightdown) {
        super();
        this.status=status;
        this.left = left;
        this.right = right;
        this.center = center;
        this.down = down;
        this.up = up;
        this.leftup = leftup;
        this.leftdown=leftdown;
        this.rightup=rightup;
        this.rightdown=rightdown;
    }



    public int getStatus(){
        return status;
    }
    public void setStatus(){
        this.status=status;
    }

    public String getLeft(){
        return left;
    }
    public void setLeft(){
        this.left=left;
    }

    public String getRight(){
        return right;
    }
    public void setRight(){
        this.right=right;
    }

    public String getCenter(){
        return center;
    }
    public void setCenter(){
        this.center=center;
    }

    public String getDown(){
        return down;
    }
    public void setDown(){
        this.down=down;
    }

    public String getUp(){
        return up;
    }

    public void setLeftUp(){
        this.leftup=leftup;
    }
    public String getLeftUp(){
        return leftup;
    }
    public void setLeftDown(){
        this.leftdown=leftdown;
    }

    public String getLeftDown(){
        return leftdown;
    }

    public String getRightDown(){
        return rightdown;
    }
    public void setRightDown(){
        this.rightdown=rightdown;
    }
    public String getRightUp(){
        return rightup;
    }
    public void setRightUp(){
        this.rightup=rightup;
    }


    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();


        sb.append("1"+leftup);
        sb.append("2"+up);
        sb.append("3"+rightup);
        sb.append("4"+left);
        sb.append("5"+center);
        sb.append("6"+right);
        sb.append("7"+leftdown);
        sb.append("8"+down);
        sb.append("9"+rightdown);


        return sb.toString();


    }





}
