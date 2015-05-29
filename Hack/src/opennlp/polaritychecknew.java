package opennlp;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class polaritychecknew {
	//static boolean isNeutral;
	public static void main(String[] args) throws Exception {
		BufferedReader inputValues = new BufferedReader(new FileReader("HackathonInput.txt"));
		BufferedWriter outputValues = new BufferedWriter(new FileWriter("HackathonOutput.txt"));
		//BufferedReader allValues = new BufferedReader(new FileReader("allValues.txt"));
		String input;
		sentimentalAnalysisIntf Sentiments = new sentimentalAnalysisIntf();
		//input="weather is weather";
		while((input = inputValues.readLine()) != null){
			//String input ="Been a loyal customer for 3 years enjoying the service, the last three months has been horrible";
			String output = "Neutral";
			//isNeutral = true;
			String all;
			//input = removeEnds(input.trim());
			System.out.println("input1-"+input);
			input = Sentiments.formatInput(input.trim());
			//input = removeEnds("I have been talking to 5 agents so far, still no end to my pain... phone can't be dead for 10 days!!!");
			/*negative = new BufferedReader(new FileReader("negativeConjunctions.txt"));
			BufferedReader nots = new BufferedReader(new FileReader("negations.txt"));
			String line;
			String[] formattedInput;
			String negate;
			if(input.contains(".")){
				formattedInput = input.split("\\.");
				input = formattedInput[formattedInput.length-1];
			}
			System.out.println("input2-"+input);
			while((line = negative.readLine()) != null){
				if((input.toUpperCase()).contains(line.toUpperCase())){
					System.out.println("line"+line);
					formattedInput= input.split(line);
					System.out.println("formattedInput"+formattedInput.length+", "+formattedInput[formattedInput.length-1]);
					input = formattedInput[formattedInput.length-1];
				}
			}
			System.out.println("input3-"+input);
			while((negate = nots.readLine()) != null){
				if((input.toUpperCase()).contains(negate.toUpperCase())){
					input= input.replaceAll(negate, "");
					isNot=true;
				}
			}
			System.out.println("input-"+input);*/
			//twitterCategorizer.trainModel();
			output=Sentiments.checkPolarity(input);
			//output=twitterCategorizer.classifyNewTweet(input);
			outputValues.write(output);
			outputValues.newLine();
		}
		outputValues.close();
		inputValues.close();
	}

	/*public void trainModel() {
		InputStream dataIn = null;
		try {
			dataIn = new FileInputStream("inputs.txt");
			ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			System.out.println("lineStream"+lineStream);
			ObjectStream sampleStream = new DocumentSampleStream(lineStream);
			System.out.println("sampleStream"+sampleStream);
			// Specifies the minimum number of times a feature must be seen
			int cutoff = 1;
			int trainingIterations = 1;
			model = DocumentCategorizerME.train("en", sampleStream, cutoff,
					trainingIterations);
			System.out.println("<MOD>"+model);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dataIn != null) {
				try {
					dataIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/

	public static String removeEnds(String input) throws Exception{
		String text = input;
		while(text.lastIndexOf(".")==text.length()-1 || text.lastIndexOf("!")==text.length()-1){
			text = text.substring(0, text.length()-1);
		}
		return text;
	}
	/*public String classifyNewTweet(String tweet) {
		String result= "";
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
		double[] outcomes = myCategorizer.categorize(tweet);
		System.out.println("tweet"+tweet);
		for (int i=0; i<outcomes.length;i++){
			System.out.println("outcomes"+outcomes[i]);
		}
		String category = myCategorizer.getBestCategory(outcomes);
		System.out.println("category"+category);
		if(isNeutral){
			System.out.println("The tweet is neutral :| ");
			result="Neutral";
		}else 
		if ((category.equalsIgnoreCase("1") && !isNot)||(!category.equalsIgnoreCase("1") && isNot)) {
			System.out.println("The tweet is positive :) ");
			result="Positive";
		} else {
			System.out.println("The tweet is negative :( ");
			result="Negative";
		}
		return result;
	}*/
}

