import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Stack;

public class PLCDecoder
{
	public static void main(String[] args) throws IOException
	{
		Scanner keyboard = new Scanner(System.in);
		Scanner PLCReader;
		System.out.print("enter the name of a PLC file: ");
		File filename = new File(keyboard.nextLine());
		PLCReader = new Scanner(filename);
		
		int inputCount = PLCReader.nextInt();
		int outputCount = PLCReader.nextInt();
		boolean[] inputs = new boolean[inputCount];
		boolean[] outputs = new boolean[outputCount];
		
		for(int i = 0;i<inputCount;i++)
		{
			System.out.print("enter a boolean value for input "+i+": ");
			inputs[i] = keyboard.nextBoolean();
		}
		
		outputs = decode (inputs,filename);
		
		for(int i = 0;i<outputCount;i++)
		{
			System.out.println("output "+i+" = "+outputs[i]);
		}
	}
		
	public static boolean[] decode(boolean[] inputs, File filename) throws FileNotFoundException
	{
		Scanner PLCReader = new Scanner(filename);
		int inputCount = PLCReader.nextInt();
		int outputCount = PLCReader.nextInt();
		PLCReader.nextLine();
		boolean[] outputs = new boolean[outputCount];
		
		
		for(int i=0;i<outputCount;i++)
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
						//System.out.println(outputs[i]);
						break;
					}
					else
					{
						System.out.println("invalid PLC file");
						System.exit(0);
					}
				}
			}
			//System.out.println();
		}
		return outputs;
	}
}
