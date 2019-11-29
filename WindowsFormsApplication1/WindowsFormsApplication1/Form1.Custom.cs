using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using WindowsFormsApplication1.util;
namespace WindowsFormsApplication1
{
    public partial class Form1
    {
        private const String cmdBase = "cd \\ && cd /java/readingbee_audio_aligner/Aligner && java -Xms125m -Xmx512m -classpath C:/java/readingbee_audio_aligner/Aligner/*;C:/java/readingbee_audio_aligner/Aligner/lib/* alignment.AlignMainNew";
        // method is the same is processText() except the last bit in commend
        private void processTextUseOldFindReplace()
        {
            setMouseEntered(false);
            String cmd2 = " 0 0 0 " + getReadingPace() + " " + getShiftBits() + " 1 " + getWavClipSec();
            processTextCMD(cmd2);
        }


        private void setEnableProcessingButtons(Boolean buttonsEnable){

            if (buttonsEnable)
            {
                button4.Enabled = true;
                button7.Enabled = true;
            }
            else
            {
                button4.Enabled = false;
                button7.Enabled = false;
            }

        
        }


        private int getShiftBits()
        {
            
            return this.comboBox1.SelectedIndex;
        }

        private double getReadingPace()
        {
            double readingPace = defaultReadingPace;
            try
            {
                readingPace = Double.Parse(comboBox2.Text);

            }
            catch (Exception e)
            {
                readingPace = defaultReadingPace;
            }

            return readingPace;
        }

        private int getWavClipSec()
        {
            int wavClipSec = defaultWavClipSec;
            try
            {
                wavClipSec = int.Parse(comboBox3.Text);

            }
            catch (Exception e)
            {
                wavClipSec = defaultWavClipSec;
            }
            return wavClipSec;
        }

        private void setWavNPlay(Boolean okToPlay)
        {
            if (textBox2.Text.Contains("wav"))
            {
                if (!this.wmplayer.URL.Equals(textBox2.Text))
                {
                    this.wmplayer.uiMode = "full";
                    this.wmplayer.URL = textBox2.Text;
                }
             //   this.wmplayer.Ctlcontrols.play();
           //     Console.WriteLine("Play WAV");
            }

            if (this.wmplayer.URL.Length < 1)
            {
                this.wmplayer.uiMode = "full";
                this.wmplayer.URL = @"c:/TEMP/e.wav";
            }
            else if (this.wmplayer.status == "Paused" && okToPlay==true)
            {
                //  this.textBox1.Text = (this.wmplayer.Ctlcontrols.currentPosition + "ok: " + this.wmplayer.status);
                this.wmplayer.Ctlcontrols.play();
            }
            else if (this.wmplayer != null)
            {
                //    this.textBox1.Text = (this.wmplayer.Ctlcontrols.currentPosition + "ok: " + this.wmplayer.status);
                this.wmplayer.Ctlcontrols.pause();
            }
         
        }

        private void setTextBox1BeginMark()
        {
            int caret_in = textBox1.SelectionStart + textBox1.SelectionLength;
            String textToSave = textBox1.Text;
            //String textToSave = textBox1.Text + caret_in;
            int lastIndexOfEnd = textToSave.LastIndexOf("[BEGIN", caret_in);
            if (lastIndexOfEnd >= 0)
            {
                String subTextToSave = textToSave.Substring(lastIndexOfEnd);
                String timeStart = StringProcessing.findStr(subTextToSave, "[BEGIN", "]").Trim();
                int timeStartInt = StringProcessing.getSecFromTimeFormat(timeStart);
                if (timeStartInt >= 0)
                {
                    if (!this.wmplayer.URL.Equals(textBox1.Text))
                    {
                        setWavNPlay(true);
                    }
                    this.wmplayer.Ctlcontrols.currentPosition = timeStartInt;

                }
            }
            textBox1.Text = textToSave;
        }

        private void setTextBox1EndMark()
        {
            int caret_in = textBox1.SelectionStart + textBox1.SelectionLength;
            String textToSave = textBox1.Text;
            int lastIndexOfEnd = textToSave.LastIndexOf("[END", caret_in);
            if (lastIndexOfEnd >= 0)
            {
                if (textToSave.IndexOf("]", lastIndexOfEnd) > lastIndexOfEnd)
                {
                    textToSave = textToSave.Substring(0, textToSave.IndexOf("]", lastIndexOfEnd) + 1);
                };
            }
            textBox1.Text = textToSave;
        }

        public static void ThreadProc()
        {
            //  system(cmd1);
            new System.Diagnostics.ProcessStartInfo("cmd", "/c dir");
        }

        private Boolean processText()
        {
            String cmd2 = " 0 0 0 " + getReadingPace() + " " + getShiftBits() + " 0 " + getWavClipSec();
            return processTextCMD(cmd2);
        }
        private Boolean processTextCMD(String cmd2)
        {
            String wavFile = textBox2.Text.Replace("\\", "/");
            String folderPath = FileProcessing.getFilePath(wavFile);
            String fileName = FileProcessing.getFileName(wavFile);
            String cmd1 = cmdBase+" \"" + folderPath + "\" " + "\"" + fileName + "\"";
            cmd1 += cmd2;
            WindowsFormsApplication1.util.Form1Util.ExecuteCommandAsync(cmd1);
            return true;
        }

        private void saveText(Boolean async)
        {

            String statusOutput = "";

            setMouseEntered(false);
            String origWAVFileName = textBox2.Text.Replace("\\", "/");
            String jTP_in1 = FileProcessing.getInsTXTFileFromWav(origWAVFileName);
            String jTP_in3 = FileProcessing.getFinalTXTFileFromWav(origWAVFileName);
            String jTp_in4 = FileProcessing.getOrigTXTFileFromWav(origWAVFileName); 
            String jTp_in5 = FileProcessing.getTXTProcessingFileFromWav(origWAVFileName);

            if (jTP_in1.Length < 5)
            {
                jTP_in1 = "C:/TEMP/advanced1/";
                Console.WriteLine("JPT1##########0" + jTP_in1);
            }
            else if (jTP_in1.IndexOf(".txt") > 1)
            {

                String textToSave = textBox1.Text;
                String textToSave3 = textBox3.Text;
                String textToSave4 = textBox4.Text;

                String startTime = "";

                
                if (textToSave.Length < 12)
                {
                    File.Delete(jTp_in5);
                    statusOutput += ("Processing File Reseted.  ");

                } 
                    if(textToSave.Length < 15) {
                    int startBracket = textToSave.IndexOf("[");
                    int endBracket = textToSave.IndexOf("]");
                    if (startBracket >= 0 && (startBracket < endBracket))
                    {
                        startTime = textToSave.Substring(startBracket, endBracket+1);
                        label5.Text = startTime;
                        startTime = startTime.Replace("[END", "[");

                        String fileProcessingString = FileProcessing.fileToString(jTp_in4);
                        if (fileProcessingString.IndexOf("[") != 0)
                        {
                            FileProcessing.copyStringToFile(startTime + fileProcessingString, jTp_in5);
                            statusOutput += ("Applied start timestamp: " + startTime+".  ");

                        }
 
                    }

                    
                }


                try
                {
                    FileProcessing.copyStringToFile(textToSave, jTP_in1);
                    FileProcessing.copyStringToFile(textToSave3, jTP_in3);
                    FileProcessing.copyStringToFile(textToSave4, jTp_in4);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
                Console.WriteLine("JPT1##########" + jTP_in1);
                Console.WriteLine("textToSave##########" + textToSave);

                String folderPath = FileProcessing.getFilePath(origWAVFileName);
                String fileName = FileProcessing.getFileName(origWAVFileName);
                String cmd1 = cmdBase+" \"" + folderPath + "\" " + "\"" + fileName + "\"" + " 0 0 1";
                if (async)
                {
                    WindowsFormsApplication1.util.Form1Util.ExecuteCommandAsync(cmd1);
                }
                else
                {
                    WindowsFormsApplication1.util.Form1Util.ExecuteCommandSync(cmd1);

                }
                //      savePrefence();
            }
            //   jButton7.setEnabled(true);
            //   jButton10.setEnabled(true);
            setEnableProcessingButtons(true);

            label4.Text = statusOutput;

        }

        private Boolean refreshjTextField1()
        {
            String origWAVFileName = textBox2.Text.Replace("\\", "/");
            String jTP_in1 = FileProcessing.getInsTXTFileFromWav(origWAVFileName);
            String origTxtFile = FileProcessing.getTXTFileFromWav(origWAVFileName);
            if (origTxtFile.IndexOf(".txt") > 1)
            {
                String origTxtFileStr = FileProcessing.fileToString(origTxtFile);

                if (!textBox4.Text.Equals(origTxtFileStr))
                {
                    textBox4.Text = origTxtFileStr;
                }
            }

            if (jTP_in1.Length < 5)
            {
                jTP_in1 = "C:/TEMP/advanced1/";
            }
            else if (jTP_in1.IndexOf(".txt") > 1)
            {
                String jTP_in1_str = FileProcessing.fileToString(jTP_in1);
                if (!textBox1.Text.Equals(jTP_in1_str))
                {
   
                        textBox1.Text = jTP_in1_str;
                    
                }
                textBox1.SelectionStart = textBox1.Text.Length;
                textBox1.ScrollToCaret();

                String jTP_in3 = FileProcessing.getFinalTXTFileFromWav(origWAVFileName);
                String jTP_in3_str = FileProcessing.fileToString(jTP_in3);
                int cursor_in = textBox3.SelectionStart;
                if (!textBox3.Text.Equals(jTP_in3_str))
                {
                    textBox3.Text = jTP_in3_str;
                }
                if (cursor_in >= 0)
                {
                    textBox3.SelectionStart = cursor_in;
                } else
                {
                    textBox3.SelectionStart = textBox3.Text.Length;
                }
                    textBox3.ScrollToCaret();
                return true;
            }
            return false;
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            if (!mouseEntered)
            {
                refreshjTextField1();
            }
        }

        private void doubleClickBeginEnd()
        {
            int caret_in = textBox1.SelectionStart;
            String textToSave = textBox1.Text;
            int curTimeInt = (int)this.wmplayer.Ctlcontrols.currentPosition;
            int[] timeStartIntFound = new int[1] { 0 };
            char[] separators = { ',', '.', ';', '!', '?', '\n', ' ' };
            try
            {
                caret_in = textToSave.LastIndexOfAny(separators, caret_in);
            }
            catch (Exception e3) { }
            if (caret_in < 0) { return; }
            textBox1.Text = StringProcessing.setjTextArea1AddEndBeginMark(textToSave, caret_in, curTimeInt, timeStartIntFound);
            if (timeStartIntFound[0] > 0)
            {
                this.wmplayer.Ctlcontrols.currentPosition = timeStartIntFound[0];
            }
        }

        private Boolean convertMP3toWav(String mp3fileIn, String wavfileOut)
        {
            if (!(mp3fileIn.ToLower().Contains("mp3") || mp3fileIn.ToLower().Contains("ogg")) || !wavfileOut.ToLower().Contains("wav"))
            {
                return false;
            }
            String cmd1 = "C:/java/readingbee_audio_aligner/Aligner/ffmpeg -i \"" + mp3fileIn.Trim() + "\" -acodec pcm_s16le -ar 16000 -ab 16000 -ac 1 \"" + wavfileOut.Trim()+"\"";
            WindowsFormsApplication1.util.Form1Util.ExecuteCommandSync(cmd1);

            return true;
        }


        private void manualForwardText(Boolean appendTime)
        {
            setMouseEntered(false);
            String origWAVFileName = textBox2.Text.Replace("\\", "/");
            String jTP_in1 = FileProcessing.getInsTXTFileFromWav(origWAVFileName);
            String jTP_in3 = FileProcessing.getFinalTXTFileFromWav(origWAVFileName);
            String jTP_in2 = FileProcessing.getTXTFileFromWav(origWAVFileName);
            String jTP_in4 = FileProcessing.getTXTProcessingFileFromWav(origWAVFileName);



            if (origWAVFileName.ToLower().Contains("wav"))
            {
                String processingTXTFileContent = "";
                if (System.IO.File.Exists(jTP_in4))
                {
                    processingTXTFileContent = FileProcessing.fileToString(jTP_in4);
                }
                else if (System.IO.File.Exists(jTP_in2))
                {
                    processingTXTFileContent = FileProcessing.fileToString(jTP_in2);
                }
                char[] separators_ = { ',', '.', ';', '!', '?', '\n',':'};
                char[] separators  = { '.', ';', '!', '?', '\n', ':'};
                char[] separators2 = { '.', ';', '!', '?', '\n','\'','"','(',')',' ',':' };
             //   char[] separators2_ = { '.', ';', '!', '?', '\n', '\'', '"', '(', ')', ' ',':',','};

                //char[] separators2 = { '.','\n',' '};
                int firstSentenceLocation = 0;
                int wordStartLocation = 0;
                if (processingTXTFileContent.Length > 25)  // check length of text remainding meets minium of 25 to process
                {
                    firstSentenceLocation = processingTXTFileContent.IndexOfAny(separators, 25) + 1; // check for . after 25
                    if (firstSentenceLocation > 75)  // if period is found after 75, then check for comma
                    {
                        firstSentenceLocation = processingTXTFileContent.IndexOfAny(separators_, 25) + 1; // if comma is found then use this as the location
                    }
                    wordStartLocation = processingTXTFileContent.LastIndexOfAny(separators2, firstSentenceLocation-2);  // find the last word of the sentence

                }

                if (wordStartLocation > 0 && (firstSentenceLocation - wordStartLocation) < 4 && (firstSentenceLocation - wordStartLocation) > 2 && firstSentenceLocation > 0)
                {
                    firstSentenceLocation = processingTXTFileContent.IndexOfAny(separators, firstSentenceLocation) + 1;
                    if (firstSentenceLocation > 2)
                    {
                        wordStartLocation = processingTXTFileContent.LastIndexOfAny(separators2, firstSentenceLocation - 2);  // find the last word of the sentence
                    }
                }

                if (wordStartLocation > 0 && (firstSentenceLocation - wordStartLocation) < 5 && (firstSentenceLocation - wordStartLocation) > 2 && firstSentenceLocation > 0)
                {
                    String tempStr = processingTXTFileContent.Substring(wordStartLocation, firstSentenceLocation - wordStartLocation);   // get the last world

                    Console.Write("**********TEMPSTR:" + tempStr + "**");
                    if (tempStr.IndexOf("St.") > -1 || tempStr.IndexOf("Mr.") > -1 || tempStr.IndexOf("MR.") > -1 || tempStr.IndexOf("Dr.") > -1 || tempStr.IndexOf("Sr.") > -1 || tempStr.IndexOf("Jr.") > -1 || tempStr.IndexOf("Ms.") > -1)  // check if it is one of these strings, if so find the next matching .
                    {

                        firstSentenceLocation = processingTXTFileContent.IndexOfAny(separators, firstSentenceLocation) + 1;
                        if (firstSentenceLocation > 2)
                        {
                            wordStartLocation = processingTXTFileContent.LastIndexOfAny(separators2, firstSentenceLocation - 2);
                        }
                    }
                }

                if (wordStartLocation > 0 && (firstSentenceLocation - wordStartLocation) < 4 && (firstSentenceLocation - wordStartLocation) > 2 && firstSentenceLocation > 0)
                {
                    firstSentenceLocation = processingTXTFileContent.IndexOfAny(separators, firstSentenceLocation) + 1;
                    if(firstSentenceLocation > 2) {
                        wordStartLocation = processingTXTFileContent.LastIndexOfAny(separators2, firstSentenceLocation-2);
                    }                }
                if (wordStartLocation > 0 && (firstSentenceLocation - wordStartLocation) < 4 && (firstSentenceLocation - wordStartLocation) > 2 && firstSentenceLocation > 0)
                {
                    firstSentenceLocation = processingTXTFileContent.IndexOfAny(separators, firstSentenceLocation) + 1;
                    if(firstSentenceLocation > 2) {
                        wordStartLocation = processingTXTFileContent.LastIndexOfAny(separators2, firstSentenceLocation-2);
                    }                
                }

                if (firstSentenceLocation > 0)
                {
                    String firstSentence = processingTXTFileContent.Substring(0, firstSentenceLocation);
                    if (firstSentence.IndexOf('[') >= 0)
                    {
                        if (appendTime)
                        {
                            firstSentence = firstSentence.Replace("[", "[BEGIN");
                        }
                    }
                    else
                    {
                        if (appendTime)
                        {

                            int curTimeInt = (int)this.wmplayer.Ctlcontrols.currentPosition;

                            if (curTimeInt > 0)
                            {


                                String curTime = StringProcessing.getTimeFormat(curTimeInt);
                                if (textBox1.Text.Length >= 12 && textBox1.Text.Length < 24)
                                {
                                    firstSentence = "[BEGIN" + curTime + "]" + firstSentence;
                                }
                                else
                                {
                                    firstSentence = "[END" + curTime + "]" + "[BEGIN" + curTime + "]" + firstSentence;
                                }
                            }
                            else
                            {
                                String curTime = StringProcessing.getTimeFormat(curTimeInt);


                                if (textBox1.Text.Length >= 12 && textBox1.Text.Length < 24)
                                {
                                    firstSentence = "[BEGIN00:00]" + firstSentence;
                                }
                                else
                                {
                                    firstSentence = "[END00:00]" + "[BEGIN00:00]" + firstSentence;
                                }
                            }
                        }
                    }
                    String remindingSentence = processingTXTFileContent.Substring(firstSentenceLocation);
                    FileProcessing.appendStringToFile(firstSentence, jTP_in1);
                    FileProcessing.copyStringToFile(remindingSentence, jTP_in4);
                }
            }
            
            refreshjTextField1();
            //   jButton7.setEnabled(true);
            //   jButton10.setEnabled(true);
        }


    }
}
