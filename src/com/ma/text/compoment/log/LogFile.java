package com.ma.text.compoment.log;



public class LogFile
{
	private static ILogger logger = new PrintToFileLogger();
	
	public static void LogD(String msg)
	{
		logger.d("System.out", msg);
	}
	
	public static void LogE(String msg)
	{
		logger.e("System.out", msg);
	}
	
	public static  void close()
	{
		logger.close();
	}
}
