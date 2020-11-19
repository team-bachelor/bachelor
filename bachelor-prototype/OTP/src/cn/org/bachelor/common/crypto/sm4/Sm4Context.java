package cn.org.bachelor.common.crypto.sm4;

public class Sm4Context
{
	public int mode;
	
	public long[] sk;
	
	public boolean isPadding;

	public Sm4Context()
	{
		this.mode = 1;
		this.isPadding = true;
		this.sk = new long[32];
	}
}
