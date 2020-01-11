
/**
 * Write a description of class periodicTable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class periodicTable
{
    private int atmcNum = 0;
    private String name = "";
    private String symbol = "";
    periodicTable(int aN, String n, String s)
    {
        atmcNum = aN;
        name = n;
        symbol = s;
    }
    
    public int getaN()
    {
        return atmcNum;
    }
    public String getN()
    {
        return name;
    }
    public String getS()
    {
        return symbol;
    }
    
    public void setaN(int aN)
    {
        atmcNum = aN;
    }
    public void setN(String N)
    {
        name = N;
    }
    public void setS(String s)
    {
        symbol = s;
    }
}
