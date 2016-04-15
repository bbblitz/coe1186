import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Stack;
import java.util.InputMismatchException;

public class PLCDecoder
{	
	public static boolean[] decode(boolean[] inputs, File filename)
	{
		return decode(inputs,false,filename);
	}
		
	public static boolean[] decode(boolean[] inputs, boolean route, File filename)
	{
		Scanner PLCReader = null;
		try
		{
			PLCReader = new Scanner(filename);
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("PLC File Not Found");
		}
	
		int inputCount = PLCReader.nextInt();
		int outputCount = PLCReader.nextInt();
		int switchCount = PLCReader.nextInt();
		int crossingCount = PLCReader.nextInt();
		PLCReader.nextLine();
		boolean[] outputs = new boolean[outputCount+switchCount+crossingCount];
		
		
		for(int i=0;i<outputCount+switchCount+crossingCount;i++)
		{
			Stack<Boolean> operands = new Stack();
			//System.out.print(i+" = ");
			while(true)
			{
				String operator;
				if(PLCReader.hasNextInt())
				{
					operands.push(inputs[PLCReader.nextInt()]);
				}
				else if (PLCReader.hasNext())
				{
					operator = PLCReader.next();
					//System.out.println("operator = "+operator);
				
					if(operator.equals("&"))
					{
						//System.out.print("AND");
						boolean A = operands.pop();
						boolean B = operands.pop();
						//System.out.print(A);
						//System.out.print(B);
						operands.push(A&&B);
					}
					else if(operator.equals("+"))
					{
						//System.out.print("OR");
						boolean A = operands.pop();
						boolean B = operands.pop();
						//System.out.print(A);
						//System.out.print(B);
						operands.push(A||B);
					}
					else if(operator.equals("!"))
					{
						//System.out.print("NOT");
						boolean A = operands.pop();
						//System.out.print(A);
						operands.push(!A);
					}
					else if(operator.equals("v"))
					{
						//System.out.print("Vcc");
						operands.push(true);
					}
					else if(operator.equals("g"))
					{
						//System.out.print("GND");
						operands.push(false);
					}
					else if(operator.equals(";"))
					{
						outputs[i] = operands.pop();
						PLCReader.nextLine();
						//System.out.println(outputs[i]);
						break;
					}
					else if(operator.equals("q"))
					{
						//push whatever value we store that we got from the CTC's yard queue
						operands.push(route);
					}
					else
					{
						System.err.println("invalid PLC file");
					}
				}
			}
			//System.out.println();
		}
		return outputs;
	}
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		Scanner PLCReader = null;
		System.out.print("enter the name of a PLC file: ");
		File filename = new File(keyboard.nextLine());
		try
		{
			PLCReader = new Scanner(filename);
		}
		catch(FileNotFoundException fnfe)
		{
			System.err.println("File not found");
		}
		int inputCount = PLCReader.nextInt();
		int outputCount = PLCReader.nextInt();
		int switchCount = PLCReader.nextInt();
		int crossingCount = PLCReader.nextInt();
		boolean[] inputs = new boolean[inputCount];
		boolean[] outputs = new boolean[outputCount+switchCount+crossingCount];
		
		for(int i = 0;i<inputCount;i++)
		{
			System.out.print("enter a boolean value for input "+i+": ");
			try
			{
				inputs[i] = keyboard.nextBoolean();
			}
			catch(InputMismatchException ime)
			{
				if(keyboard.hasNextInt() && keyboard.nextInt()==1) inputs[i]=true;
				else inputs[i]=false;
			}
		}
		
		outputs = decode(inputs,filename);
		
		for(int i = 0;i<outputCount;i++)
		{
			System.out.println("block "+i+" = "+outputs[i]);
		}
		for(int i=0;i<switchCount;i++)
		{
			System.out.println("switch "+i+" = "+outputs[i+outputCount]);
		}
		for(int i=0;i<crossingCount;i++)
		{
			System.out.println("crossing "+i+" = "+outputs[i+outputCount+switchCount]);
		}
	}
}
