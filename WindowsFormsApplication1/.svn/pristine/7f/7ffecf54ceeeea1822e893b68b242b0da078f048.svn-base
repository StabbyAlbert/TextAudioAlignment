using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using WindowsFormsApplication1.util;

namespace WindowsFormsApplication1.util
{
    class StringProcessing
    {
        public static String bufFilter(String buf5)
        {
            StringBuilder buf4 = new StringBuilder(buf5);
            int start5 = buf4.ToString().IndexOf("[");
            int end5 = buf4.ToString().IndexOf("]", start5);
            while (start5 >= 0 && end5 > 0)
            {
                buf4 = buf4.Remove(start5, end5 + 1);
                start5 = buf4.ToString().IndexOf("[");
                end5 = buf4.ToString().IndexOf("]", start5);
            }
            String buf4_Str = buf4.ToString();
            return buf4_Str;
        }

        public static StringBuilder bufFilter_StringBuilder(StringBuilder buf1)
        {
            int start = buf1.ToString().IndexOf('[');
            int end = 0;
            if (start >= 0)
            {
                end = buf1.ToString().IndexOf(']', start);
            }
            while (start >= 0 && end > 0 && ((end+1) < buf1.Length))
            {
                Console.WriteLine("Buf1: "+buf1);
                Console.WriteLine("Buf1 Length: " + buf1.Length);
                Console.WriteLine("Start: " + start);
                Console.WriteLine("End: " + end);
                buf1.Remove(start, end-start + 1);
                start = buf1.ToString().IndexOf('[', start);
                if (start >= 0)
                {
                    end = buf1.ToString().IndexOf(']', start);
                }
            }
            return buf1;
        }

        public static StringBuilder bufFilter_StringBuilder2(StringBuilder buf1)
        {
            int start = buf1.ToString().IndexOf('(');
            int end = 0;
            if (start >= 0)
            {
                end = buf1.ToString().IndexOf(')', start);
            }
            while (start >= 0 && end > 0 && ((end + 1) < buf1.Length))
            {
                Console.WriteLine("Buf1: " + buf1);
                Console.WriteLine("Buf1 Length: " + buf1.Length);
                Console.WriteLine("Start: " + start);
                Console.WriteLine("End: " + end);

                buf1.Remove(start, end - start + 1);
                start = buf1.ToString().IndexOf('(', start);
                if (start >= 0)
                {
                    end = buf1.ToString().IndexOf(')', start);
                }
            }
            return buf1;
        }	

        public static int getSecFromTimeFormat(String timeStr)
        {
            if (timeStr == null || timeStr.Length < 5)
            {
                return -1;
            }
            String secStr = "0";
            String minStr = "0";
            if (timeStr.IndexOf(":") > 0)
            {
                secStr = timeStr.Substring(timeStr.IndexOf(":") + 1);
                minStr = timeStr.Substring(0, timeStr.IndexOf(":"));
                int outInt = -1;
                try
                {
                    outInt = int.Parse(secStr) + int.Parse(minStr) * 60;
                    return outInt;
                } catch (Exception e6) {};
            }
            return 0;
        }


        public static String getTimeFormat(double savedMediaLength)
        {
            String timeFormated = "";
           // NumberFormat format; // Format minutes:seconds with leading zeros
           // format = NumberFormat.getNumberInstance();
           // format.setMinimumIntegerDigits(2); // pad with 0 if necessary
            // Convert remaining milliseconds to mm:ss format and display 
            if (savedMediaLength < 0) savedMediaLength = 0;
            int minutes = (int)(savedMediaLength / 60);
            int seconds = (int)(savedMediaLength % 60);
            timeFormated = (minutes.ToString("D2") + ":" + seconds.ToString("D2"));
            return timeFormated;
        }



        public static String insertTimeTag(String str_in, int startPosition, String curTime)
        {
            if (str_in.Length <= startPosition || startPosition < 0)
            {
                Console.WriteLine("skip insert");
                return str_in;
            }
            Console.WriteLine("insert start");
            String endOfLine = Convert.ToString((char) (10)) + Convert.ToString((char) (13));
            String str1 = str_in.Substring(0, startPosition);
            String str2 = str_in.Substring(startPosition);

            int startPosition2 = startPosition;
            while (str2.StartsWith(Convert.ToString((char)(10))) || str2.StartsWith(Convert.ToString((char)(13))))
            {
                startPosition2 = startPosition2 + 1;
                str1 = str_in.Substring(0, startPosition2);
                str2 = str_in.Substring(startPosition2);
            }


            String strOut = str1 + "[" + curTime + "]" + str2;


        //    Console.WriteLine("Insert Line1 ");
       /*     if (str1.EndsWith(endOfLine))
            {
                Console.WriteLine("Insert Line2 ");

                strOut = str1 + "[" + curTime + "]" + str2;
            }
        */
            return strOut;
        }

        public static String insertEndBeginTag(String str_in, int startPosition, String curTime)
        {
            if (str_in.Length <= startPosition || startPosition < 0)
            {
                Console.WriteLine("skip insert");
                return str_in;
            }
            Console.WriteLine("insert start");
                String str1 = str_in.Substring(0, startPosition);
                String str2 = str_in.Substring(startPosition);
                String strOut = str1 + "[END" + curTime + "]" + "[BEGIN" + curTime + "]"+str2;
                return strOut;
        }

                public static StringBuilder insertBeginEndTag(StringBuilder str_in, int statPosition) {
                        StringBuilder fileContentBuff = str_in;
                     //     String topSearchText = "";
                     //      String midSearchText = "\\[END[.\\r\\n]*\\[BEGIN[.\\r\\n]*\\]";
                     //   String bottomSearchText = "";                  
                        String topSearchText = "\\[END";
                        String midSearchText = "\\[BEGIN";
                        String bottomSearchText = "\\]";
                        String replacementText = "";
                        String[] foundTextPre = new String[3];
                        String[] foundText = new String[3];
                        foundText[0] = "";  // return found string
                        foundText[1] = "";  // return previous string before found string
                        foundText[2] = "";  // return next string after found string
                        int startPosition = statPosition;
                        int numReplaced = 0;
                        int[] bottomPosition = new int[1];
                        bottomPosition[0] = 0;                     
                        Boolean parseOK = parseBigChunkIgnorSpace(fileContentBuff, topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition);
                        Console.WriteLine("###parse start..");
 
                    if(parseOK) {
                                   fileContentBuff = new StringBuilder(foundText[1]);
                                   String timeStart = findStr(foundText[0],"[END","]").Trim();
                                   String timeEnd = findStr(foundText[0],"[BEGIN","]").Trim();
                                   replacementText = "";
                                   replacementText += foundText[0];
                                   Console.WriteLine("Replacement Text0:" + replacementText);

                                   replacementText = replaceFirst(replacementText, "[BEGIN", "[END" + timeEnd + "][BEGIN");

                               Console.WriteLine("Replacement Text:" + replacementText);

                               replacementText = replaceFirst(replacementText,"[END","[END"+timeStart+"][BEGIN");
                               Console.WriteLine("Replacement Text2:" + replacementText);

                            fileContentBuff.Append(replacementText);
                                   fileContentBuff.Append(foundText[2]);
                                   startPosition = foundText[1].Length + replacementText.Length;
                                   numReplaced++;
                                }
                           return fileContentBuff;
            } 

                public static String findStr(String INPUT_in, String findBegin, String findEnd)
            {
                int[] bottomPosition = new int[1];
                bottomPosition[0] = 0;
                String[] subString = new String[1];
                subString[0] = "";
                if(INPUT_in.Length > 3)
                {
                    Boolean foundStr = getStr_inBE_ignorCase(new StringBuilder(INPUT_in), findBegin,findEnd, subString, bottomPosition, 8000);
                    if(foundStr && subString[0].Length > 0) {
                        return subString[0];
                    } else {
                        return INPUT_in;
                    }
                } else
                {
                    return INPUT_in;
                }
            }


                public static String replaceFirst(String sHave,String findThis, String replacementStr)
                {
                    int iPlace;
                    String sPart;
                    iPlace = sHave.IndexOf(findThis);
                    if (iPlace < 0) return sHave;

                    sPart = sHave.Substring(0, iPlace + findThis.Length);
                    sPart = sPart.Replace(findThis, replacementStr) + sHave.Substring(iPlace + findThis.Length);

                    return sPart;
                        
                }

                public static String replaceLast(String sHave, String findThis, String replacementStr)
                {
                    int iPlace;
                    String sPart;
                    iPlace = sHave.LastIndexOf(findThis);
                    if (iPlace < 0) return sHave;

                    sPart = sHave.Substring(0, iPlace + findThis.Length);

                    String sPart2 = sHave.Substring(iPlace + findThis.Length);

                    sPart += sPart2.Replace(findThis, replacementStr);

                    return sPart;

                }


      public static Boolean getStr_inBE_ignorCase(StringBuilder originalText2, String beginString, String endString, String[] subString, int[] nextPosition, int maxLength)
        {
            int localPosition1 = nextPosition[0];
            int localPosition2 = 0;
            localPosition1 = originalText2.ToString().ToUpper().IndexOf(beginString.ToUpper(), localPosition1) + beginString.Length;
            localPosition2 = originalText2.ToString().ToUpper().IndexOf(endString.ToUpper(), localPosition1 + 1);
            if(localPosition1 != -1 + beginString.Length && localPosition2 != -1)
            {
                if(localPosition2 - localPosition1 > maxLength)
                {
                    localPosition2 = localPosition1 + 1;
                } else
                {
                    subString[0] = originalText2.ToString().Substring(localPosition1, localPosition2-localPosition1);
                    nextPosition[0] = localPosition2;
                    return true;
                }
            } else
            {
                subString[0] = "--";
            }
            return false;
        }

    public static Boolean parseBigChunkIgnorSpace(StringBuilder originalText2_in, String topSearchText_in,String midSearchText_in, String bottomSearchText_in, int startPosition, String[] foundText, int[] bottomPosition) {    	    	
        String midSearchText = midSearchText_in;    	
        if(topSearchText_in.Length > 0) {
            midSearchText = topSearchText_in+"[^~]*"+midSearchText; 
        }    	
        if(bottomSearchText_in.Length > 0) {
            midSearchText = midSearchText+"[^~]*"+bottomSearchText_in;    		
        }
        return parseBigChunkIgnorSpace(originalText2_in, midSearchText, startPosition,foundText, bottomPosition);
    }

  public static Boolean parseBigChunkIgnorSpace(StringBuilder originalText2_in, String midSearchText_in, int startPosition, String[] foundText, int[] bottomPosition) {
      String originalText = originalText2_in.ToString();
     //  String originalText =  escapeCharNewToSpace.forAUDIOTXT(originalText2_in.ToString().ToUpper());
        String midSearchText = midSearchText_in;
       while(midSearchText.Contains("  ")) {
           midSearchText = midSearchText.Replace("  ", " ");
           
       }
        
        
    Console.WriteLine("midSearchText: "+midSearchText);
        int[] start_end_out = new int[2] {-1,-1};

        Console.WriteLine("find index startPosition: " + startPosition);

        findIndex(originalText,midSearchText,start_end_out,startPosition);
        if(start_end_out[0] < 0) {
            bottomPosition[0] = -1;
            foundText[0] = "NOTHING FOUND";
            Console.WriteLine("NOTHING FOUND 0");

            return false;
        }
     /*   if(start_end_out[1] > 2000) {
            bottomPosition[0] = -1;
            foundText[0] = "NOTHING FOUND";
            Console.WriteLine("NOTHING FOUND 1");

            return false;        	
        }*/
        
               foundText[0] = originalText2_in.ToString().Substring(start_end_out[0], start_end_out[1]);
               foundText[1] = originalText2_in.ToString().Substring(0,start_end_out[0]);
               foundText[2] = originalText2_in.ToString().Substring(start_end_out[0]+start_end_out[1]);
               bottomPosition[0] = start_end_out[0]+start_end_out[1];
          //     Console.WriteLine(" originalText:" + originalText);
           //    Console.WriteLine(" midSearchText:" + midSearchText);
     // Console.WriteLine("foundText0: " + foundText[0]);
           //    Console.WriteLine("foundText1: " + foundText[1]);
              // Console.WriteLine("foundText2: " + foundText[2]);
        return true;
    }





     private static void findIndex(String textToSearch_in, String findThis, int[] start_end_out, int startPosition) {
      String textToSearch = textToSearch_in;
      if(textToSearch_in.Length > startPosition) {
          if (startPosition >= 0)
          {
              textToSearch = textToSearch.Substring(startPosition);
          }
      } else {
          return;
      }
      int[] start_end_out_l = new int[2]{-1,-1};
      findIndex(textToSearch, findThis, start_end_out_l);
      if(start_end_out_l[0] >= 0) {
          start_end_out[0] = start_end_out_l[0]+startPosition;
          if(start_end_out_l[1] >= 0) {
             start_end_out[1] = start_end_out_l[1];
          }
      }
      return;
    }



      private static void findIndex(String text, String findThis_in, int[] start_end_out) {

          Console.WriteLine("tEXTiN:" + text);
          Console.WriteLine("findThis_in: "+findThis_in);
          String findThis = findThis_in;
           
      int lastSpace = findThis.LastIndexOf(" ");
      if(lastSpace >= 0) {
          findThis = findThis.Remove(lastSpace, 1).Insert(lastSpace, "[\\s]*");
       }
      lastSpace = findThis.LastIndexOf(" ");
      if (lastSpace >= 0)
      {
          findThis = findThis.Remove(lastSpace, 1).Insert(lastSpace, "[\\s]*");
      }
        
   //   String regex = findThis.Replace(" ", "[\\s]*");
      String regex = findThis.Replace(" ", "[^~]*");
      Regex p = null;
      Match matcher = null;
      try
      {
          p = new Regex(regex,RegexOptions.Multiline);
          matcher = p.Match(text);
      }
      catch (Exception e)
      {
          Console.WriteLine("ERROR Regex:" + regex);
         
      }
   
      Console.WriteLine("Regex:" + regex);
      if (matcher != null && matcher.Success) {
          Console.WriteLine("SucessfulMatchIndex:" + matcher.Index);
          Console.WriteLine("SucessfulMatchLength:" + matcher.Length);

          start_end_out[0] = matcher.Index;
         start_end_out[1] = matcher.Length;
 //        System.out.println("MATCH FOUND...:"+matcher.start()+" :"+matcher.end());
      }
  }


        

      public static String setjTextArea1AddBeginEndMark(String textToSave, int caret_in)
      {
          int lastIndexOfEnd = textToSave.LastIndexOf("[END", caret_in);
          int lastIndexOfBegin = textToSave.LastIndexOf("[BEGIN", caret_in);
          if (lastIndexOfEnd >= 0)
          {
              if (lastIndexOfBegin > lastIndexOfEnd)
              {
                  return textToSave; // exit if begin aloready follows end in position of the cursor
              }
              int firstIndexOfBegin = textToSave.IndexOf("[BEGIN", caret_in);
              if (firstIndexOfBegin >= 0 && caret_in >= 0)
              {
                  Console.WriteLine(" LASTINDEXOFOND:" + lastIndexOfEnd);
                  StringBuilder newSBF = StringProcessing.insertBeginEndTag(new StringBuilder(textToSave), lastIndexOfEnd);
                  if (newSBF.Length > textToSave.Length)
                  {
                      return newSBF.ToString();
                  }
              }
          }
          return textToSave; // exit if begin aloready follows end in position of the cursor
      }

      public static String setjTextArea1AddEndBeginMark(String textToSave, int caret_in, int curTimeInt, int[] timeStartIntFound)
      {
          String curTime = StringProcessing.getTimeFormat(curTimeInt);
          int lastIndexOfS1 = textToSave.LastIndexOf("[BEGIN", caret_in);
          int lastIndexOfS2 = textToSave.LastIndexOf("[END", caret_in);
          if (lastIndexOfS1 >= 0)
          {
              if (lastIndexOfS2 > lastIndexOfS1)
              {
                  return textToSave; // exit if begin aloready follows end in position of the cursor
              }
              int firstIndexOfS2 = textToSave.IndexOf("[END", caret_in);
              if (firstIndexOfS2 >= 0 && caret_in >= 0)
              {
                  String subTextToSave = textToSave.Substring(lastIndexOfS1);
                  String timeStart = findStr(subTextToSave,"[BEGIN","]").Trim();
                  String timeEnd = findStr(subTextToSave,"[END","]").Trim();
                  int timeStartInt = getSecFromTimeFormat(timeStart);
                  int timeEndInt = getSecFromTimeFormat(timeEnd);
                  if (timeStartInt <= curTimeInt && curTimeInt <= timeEndInt)
                  {
                      Console.WriteLine(" INSERTING...:");
                      String newStr = insertEndBeginTag(textToSave, caret_in, curTime);
                      if (newStr.Length > textToSave.Length)
                      {
                          return newStr;
                      }
                  }
                  else
                  {
                      Console.WriteLine(timeStart+" SKIP INSERTING...:"+timeEnd+" ok:"+timeStartInt+"ok2:"+timeEndInt+"curTImeInt:"+curTimeInt);
                      if (timeStartInt > 0)
                      {
                          timeStartIntFound[0] = timeStartInt;
                      }

                  }
              }
          }
          return textToSave; // exit if begin aloready follows end in position of the cursor
      }


        public static void test()
        {
            int[] start_end_out= new int[2];
            string hello = "[END00:01]  there ok lkdjfldjfld j there   ok [BEGIN00:02]dfd[END00:03]there   ok [BEGIN00:04]dfdssaf!!!";
        //    findIndex(hello, "\\[END.*\\[BEGIN.*\\]", start_end_out);

          //  StringBuilder hello2 = insertBeginEndTag(new StringBuilder(hello), 1);

         //   Console.WriteLine(" New2: "+setjTextArea1AddBeginEndMark(hello, 20));

        //   Console.WriteLine(" FINDSTR"+findStr("TRYME  KKOK KJJK", "TRY", "OK"));
            try
            {
             //   Console.WriteLine("hello2: "+hello2);
                Console.WriteLine("--Hello there!!!" + start_end_out[0] + " end:" + start_end_out[1] + hello.Substring(start_end_out[0], start_end_out[1]));
            }
            catch (Exception e)
            {
                Console.WriteLine("Out of Range" + start_end_out[0] + " end:" + start_end_out[1]);                
            }
         
          
        }




    }
}
