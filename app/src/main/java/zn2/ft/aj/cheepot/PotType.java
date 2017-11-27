package zn2.ft.aj.cheepot;

public class PotType
{
	private String name;
	
	private int icon;	

	public PotType(String nombre, int icon)
	{
		super();
		this.name = nombre;
		this.icon = icon;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public int getIcon() 
	{
		return icon;
	}

	public void setIcon(int icon) 
	{
		this.icon = icon;
	}	

}