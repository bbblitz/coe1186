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
		//try
		//{
			PLCReader = new Scanner(filename);
		/*}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("file does not exist");
		}*/
		
		int lineCount = 0;		//reads through once to count number of lines
		while(PLCReader.hasNextLine())
		{
			
			PLCReader.nextLine();
			lineCount++;
		}
		PLCReader.close();
		PLCReader = new Scanner(filename);
		
		
		boolean[] inputs = {true,true,true};
		boolean[] outputs = {false,false,false};
		
		
		//String[] plcode = new String[lineCount];
		for(int i=0;i<lineCount;i++)
		{
			Stack<Boolean> operands = new Stack();
			System.out.print(i+" = ");
			
			//plcode[i] = PLCReader.nextLine();
			//System.out.println(plcode[i]);
			while(true)
			{
				String operator;
				if(PLCReader.hasNextInt())
				{
					System.out.println("read input");
					operands.push(inputs[PLCReader.nextInt()]);
				}
				else if (PLCReader.hasNext())
				{
					operator = PLCReader.next();
					System.out.println("operator = "+operator);
				
					if(operator.equals("&"))
					{
						System.out.print("AND");
						boolean A = operands.pop();
						boolean B = operands.pop();
						System.out.print(A);
						System.out.print(B);
						operands.push(A&&B);
					}
					else if(operator.equals("+"))
					{
						System.out.print("OR");
						boolean A = operands.pop();
						boolean B = operands.pop();
						System.out.print(A);
						System.out.print(B);
						operands.push(A||B);
					}
					else if(operator.equals("!"))
					{
						System.out.print("NOT");
						boolean A = operands.pop();
						System.out.print(A);
						operands.push(!A);
					}
					else if(operator.equals("v"))
					{
						System.out.print("Vcc");
						operands.push(true);
					}
					else if(operator.equals("g"))
					{
						System.out.print("GND");
						operands.push(false);
					}
					else if(operator.equals(";"))
					{
						System.out.print(operands.pop());
						break;
					}
					else
					{
						System.out.println("invalid PLC file");
						System.exit(0);
					}
				}
			}
			System.out.println();
		}
		
		
	}

}
