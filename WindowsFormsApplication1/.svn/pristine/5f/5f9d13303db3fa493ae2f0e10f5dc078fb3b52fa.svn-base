using System;
using System.Collections.Generic;
using System.Linq;
using WindowsFormsApplication1.util;
namespace WindowsFormsApplication1
{
    public partial class Form1
    {
        private void doubleClickText3NoMark(Boolean AddTimeStamp)
        {                          
            int caret_in = textBox3.SelectionStart;
            String textToSave = textBox3.Text;
            int curTimeInt = (int)this.wmplayer.Ctlcontrols.currentPosition;
            String curTime = StringProcessing.getTimeFormat(curTimeInt);
          //  int[] timeStartIntFound = new int[1] { 0 };
            char[] separators = { ',', '.', ';', '!', '?', '\r', ' ' };
            try
            {
                caret_in = textToSave.LastIndexOfAny(separators, caret_in);
            }
            catch (Exception e3) { }
            if (caret_in < 0) { return; }
            if (AddTimeStamp) { textBox3.Text = textToSave = StringProcessing.insertTimeTag(textToSave, caret_in, curTime); };
            char[] separators1 = { 'A', 'B', 'C', 'D', 'E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                                   'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                                   '1','2','3','4','5','6','7','8','9','0'};
            char[] separators2 = { '.', ';', '!','?',':'};
            char[] separators3 = { '[', ']'};
            try
            {
                for (int count = 0; count < 100 && caret_in>0; count++)
                {
                    int nextBracket = 0;
                    caret_in = textToSave.IndexOfAny(separators2, caret_in)+1;
                    Console.WriteLine("Caret_in: " + caret_in);
                    //caret_in = textToSave.IndexOf.IndexOfAny(separators2, caret_in);
                    if (caret_in - 10 > 0)
                    {
                        nextBracket = textToSave.IndexOfAny(separators3, caret_in - 10);
                    }                    
                    if (Math.Abs(nextBracket - caret_in) < 25)
                    {
                        continue;
                     //   caret_in = textToSave.IndexOfAny(separators, caret_in);
                    }
                    else
                    {
                        caret_in = textToSave.IndexOfAny(separators1, caret_in);
                        break;
                    }
                }
                if (caret_in < 0 || caret_in > textToSave.Length)
                {
                    caret_in = textToSave.Length;
                }                
            } catch (Exception e3) { }
            int caret_in2 = textToSave.IndexOfAny(separators2, caret_in);
            textBox3.SelectionStart = caret_in;
            Console.WriteLine("Selection: " + caret_in);
            if (caret_in2 - caret_in > 0)
            {
                textBox3.SelectionLength = caret_in2 - caret_in;
            }
            Console.WriteLine("Selection2: " + caret_in2);
            textBox3.ScrollToCaret();
        }
    }
}
