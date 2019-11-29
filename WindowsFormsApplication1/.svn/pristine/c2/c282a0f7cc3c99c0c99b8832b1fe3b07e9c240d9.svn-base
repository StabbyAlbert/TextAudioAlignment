using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using System.Runtime.InteropServices;
using WindowsFormsApplication1.util;
using System.IO;
namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
    [DllImport("kernel32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
    public static extern int GetTickCount();

    [DllImport("kernel32.dll", SetLastError=true)]
    [return: MarshalAs(UnmanagedType.Bool)]
    static extern bool AllocConsole();

    public const int defaultWavClipSec = 35;
    public const double defaultReadingPace = 14.0;
    private volatile Boolean mouseEntered = false;
    private static String preference = "c:/temp/preferences.txt";
    private volatile int caretSaved = 0;
    private long timeFirstClicked = GetTickCount();
        System.Windows.Forms.Timer timer = new System.Windows.Forms.Timer();

        public Form1()
        {
            InitializeComponent();
            this.comboBox1.Items.Add("No Shift");
            this.comboBox1.Items.Add("Shift 1 Bit");
            this.comboBox1.Items.Add("Auto Detect");
            this.comboBox1.SelectedIndex = 2;
            this.comboBox2.Items.Add("8");
            this.comboBox2.Items.Add("9");
            this.comboBox2.Items.Add("10");
            this.comboBox2.Items.Add("11");
            this.comboBox2.Items.Add("12");
            this.comboBox2.Items.Add("13");
            this.comboBox2.Items.Add("14");
            this.comboBox2.Items.Add("15");
            this.comboBox2.Items.Add("16");
            this.comboBox2.Items.Add("17");
            this.comboBox2.Items.Add("18");
            this.comboBox3.Items.Add("10");
            this.comboBox3.Items.Add("20");
            this.comboBox3.Items.Add("30");
            this.comboBox3.Items.Add("40");
            this.comboBox3.Items.Add("60");
            this.comboBox3.Items.Add("80");
            this.comboBox3.Items.Add("100");
            this.comboBox3.Items.Add("120");
            this.comboBox3.Items.Add("140");
            this.comboBox3.Items.Add("160");
            this.comboBox3.Items.Add("180");
            this.comboBox3.Items.Add("200");
            this.comboBox3.Items.Add("220");
            this.comboBox3.Items.Add("240");
            this.comboBox3.Items.Add("260");
            this.comboBox3.Items.Add("280");
            this.comboBox3.Items.Add("300");
            this.comboBox3.Items.Add("320");
            this.comboBox2.SelectedIndex = 6;
            this.comboBox3.SelectedIndex =17;
            timer.Tick += new EventHandler(timer_Tick);
            timer.Interval = (1000)*(5);
            timer.Enabled = true;
            timer.Start();
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
           
        }

        private void button1_Click(object sender, EventArgs e)
        {
  
   
        }

        private void textBox1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            String textToSave = this.textBox1.Text;
            int caret_in = textBox1.SelectionStart + textBox1.SelectionLength;
            String textSelected = textBox1.SelectedText;
            if (textSelected.Contains("END")) {
                Console.WriteLine("END selected");
                setTextBox1EndMark();
            } else if (textSelected.Contains("BEGIN"))
            {
                Console.WriteLine("BEGIN selected");
                setTextBox1BeginMark();
                if (textBox2.Text.Contains("wav"))
                {
                    this.wmplayer.Ctlcontrols.play();
                    Console.WriteLine("Play WAV");
                }
            } else {
                Console.WriteLine("ELSE selected");
                textBox1.Text = StringProcessing.setjTextArea1AddBeginEndMark(textToSave, caret_in);
                if (textToSave.Equals(textBox1.Text))
                {
                    if (!this.wmplayer.URL.Equals(textBox2.Text))
                    {
                        setWavNPlay(true);
                    }
                    else
                    {

                    }
                    doubleClickBeginEnd();
                }
            }

       //     saveText(false);

            //   processText();

        }

        private void button2_Click(object sender, EventArgs e)
        {

        }

        private void openFileDialog1_FileOk(object sender, CancelEventArgs e)
        {
            if (openFileDialog1.FileName.ToLower().Contains("wav"))
            {
                textBox2.Text = openFileDialog1.FileName;
                refreshjTextField1();
                setWavNPlay(false);
            }
            else if (openFileDialog1.FileName.ToLower().Contains("mp3") || openFileDialog1.FileName.ToLower().Contains("ogg"))
            {
                String origMP3FileName = openFileDialog1.FileName.Replace("\\", "/");
                String origWAVFileName = FileProcessing.getWavFileFromMp3(origMP3FileName);
                textBox2.Text = "Converting...";
                if (File.Exists(origWAVFileName))
                {
                    textBox2.Text = origWAVFileName;
                } else if (convertMP3toWav(origMP3FileName, origWAVFileName))
                {
                    textBox2.Text = origWAVFileName;
                   
                    /*
                    String jTP_in1 = FileProcessing.getTXTFileFromWav(origWAVFileName);

                    Console.WriteLine("Renameing:" + jTP_in1);
                    if (System.IO.File.Exists(jTP_in1))
                    {
                        Console.WriteLine("Renameing2:" + jTP_in1);
                        FileProcessing.renameFile(jTP_in1, jTP_in1 + ".bak");
                        String processingTXTFileContent = FileProcessing.fileToStringFilter(jTP_in1 + ".bak");
                        FileProcessing.copyStringToFile(processingTXTFileContent, jTP_in1);
                        Console.WriteLine("Renameing3:" + jTP_in1);

                    }
                     */




                } else {
                    textBox2.Text = "Warning: WAV Conversion failed.";
                    Console.WriteLine("Convert failed");

                }
                
                refreshjTextField1();

                setWavNPlay(false);
            }

            if (textBox1.Text.Length < 10)
            {
                textBox1.Text = label5.Text;
                String jTP_in1 = FileProcessing.getInsTXTFileFromWav(textBox2.Text);
                FileProcessing.copyStringToFile(label5.Text, jTP_in1);

                label4.Text = "Initialized Raw File with: " + label5.Text;

            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
             openFileDialog1.ShowDialog();
//        FileProcessing.readFile(textBox2.Text);

             if (this.wmplayer != null)
             {
                 //    this.textBox1.Text = (this.wmplayer.Ctlcontrols.currentPosition + "ok: " + this.wmplayer.status);
                 this.wmplayer.Ctlcontrols.stop();
             }
             saveText(true);
             refreshjTextField1();
        }

//        const String cmd1 = "C:/java/jdk1.6.0_14/bin/java  -Xms64m -Xmx128m -classpath C:/java/auto_cue_sphinx/ProcessAudio_share/ProcessAudio_share/bin/*;C:/java/auto_cue_sphinx/ProcessAudio_share/ProcessAudio_share/libs/* alignment.AlignMain \"" + folderPath + "\" " + "\"" + FileProcessingNew.getFileName(origWAVFileName) + "\"";

        private void button4_Click(object sender, EventArgs e)
        {
            setEnableProcessingButtons(false);
            saveText(true);
            setEnableProcessingButtons(false);

            Thread.Sleep(1000);

            String statusOutput = label4.Text;

       
                statusOutput += " Processing Audio...";
                label4.Text = statusOutput;
            setMouseEntered(false);
       
            setEnableProcessingButtons(false);
            processText();
            statusOutput += " ...";
            label4.Text = statusOutput;
        }

        private void button5_Click(object sender, EventArgs e)
        {
         
            saveText(true);
            refreshjTextField1();
        }

        private void setMouseEntered(Boolean mouseEntered_in)
        {
            mouseEntered = mouseEntered_in;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            AllocConsole();
        }

        private void textBox1_CursorChanged(object sender, EventArgs e)
        {
            caretSaved = textBox1.SelectionStart;

        }

        private void textBox1_DoubleClick(object sender, EventArgs e)
        {

        }

        private void button6_Click(object sender, EventArgs e)
        {
            setMouseEntered(false);
            refreshjTextField1();
        }

        private void button7_Click(object sender, EventArgs e)
        {
            setEnableProcessingButtons(false);
            processTextUseOldFindReplace();

        }




private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
{

}

private void textBox1_MouseClick(object sender, MouseEventArgs e)
{
    setMouseEntered(true);
}

private void button8_Click(object sender, EventArgs e)
{
    saveText(true);

    setMouseEntered(false);
    String cmd2 = " 0 1 0";
    processTextCMD(cmd2);
    label4.Text = "Converted Raw to Final LRC";

}

private void textBox3_TextChanged(object sender, EventArgs e)
{

}

private void textBox3_KeyPress(object sender, KeyPressEventArgs e2)
{

}


private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
{


    if (e.KeyChar == (char)27)
    {
        doubleClickBeginEnd();
    }
    if (e.KeyChar == (char)93)
    {
        int caret_start = textBox1.SelectionStart;
        String textToSave = textBox1.Text;
        Console.Write("CHAR ] PRESSED");
        // int caret_start = textBox1.ToString().LastIndexOf("[", caret_start);
        try
        {
            caret_start = textToSave.LastIndexOf("[", caret_start);
        }
        catch (Exception e2) { };
        String textSelected = "";
        if (caret_start >= 0 && textBox1.SelectionStart > 0)
        {
            int selectionLength = textBox1.SelectionStart - caret_start;
            textSelected = textToSave.Substring(caret_start, selectionLength)+"]";
            if (textSelected.Contains("BEGIN"))
            {
                Console.WriteLine("BEGIN selected s:"+textSelected);
                //***
                String timeStart = StringProcessing.findStr(textSelected, "[BEGIN", "]").Trim();
                int timeStartInt = StringProcessing.getSecFromTimeFormat(timeStart);
                if (timeStartInt >= 0)
                {
                    if (!this.wmplayer.URL.Equals(textBox1.Text))
                    {
                        setWavNPlay(true);
                    }
                    this.wmplayer.Ctlcontrols.currentPosition = timeStartInt;

                }
                //***
                if (textBox2.Text.Contains("wav"))
                {
                    this.wmplayer.Ctlcontrols.play();
                    Console.WriteLine("Play WAV");
                }
            }
        }
    }
}

private void textBox3_MouseDoubleClick(object sender, MouseEventArgs e)
{
    setMouseEntered(true);
    String textToSave = this.textBox3.Text;
    int caret_in = textBox3.SelectionStart + textBox3.SelectionLength;
    String textSelected = textBox3.SelectedText;
    if (textSelected.Contains("[") && textSelected.Contains("]"))
    {
        Console.WriteLine("[] selected, play file");
        if (!this.wmplayer.URL.Equals(textBox2.Text))
        {
            setWavNPlay(true);
        }
        String timeStart = StringProcessing.findStr(textSelected, "[", "]").Trim();
        
        int timeStartInt = StringProcessing.getSecFromTimeFormat(timeStart);
        Console.Write("TextSelected: " + textSelected + " timeStartStr:" + timeStart + " timeStartInt:" + timeStartInt);
        if (timeStartInt > 0)
        {
            this.wmplayer.Ctlcontrols.currentPosition = timeStartInt;
        }
        this.wmplayer.Ctlcontrols.play();
      //  doubleClickBeginEnd();
        
        //   setTextBox1EndMark();
    } else
    {
        Console.WriteLine("ELSE selected");
        doubleClickText3NoMark(true);

     /*   textBox1.Text = StringProcessing.setjTextArea1AddBeginEndMark(textToSave, caret_in);
        if (textToSave.Equals(textBox1.Text))
        {
            if (!this.wmplayer.URL.Equals(textBox2.Text))
            {
                setWavNPlay();
            }
            doubleClickBeginEnd();
        }
      */
    }

  //  saveText();

}

private void textBox3_KeyDown(object sender, KeyEventArgs e)
{
    if (e.KeyCode == Keys.F1 || e.KeyCode == Keys.F11)
    {
        Console.WriteLine("F1 PRESSED");
        doubleClickText3NoMark(true);

    }
    else if (e.KeyCode == Keys.F2 || e.KeyCode == Keys.F12)
    {
        Console.WriteLine("F2 PRESSED");
        doubleClickText3NoMark(false);    
    }
    else
    {
        Console.WriteLine("Nothing PRESSED..");
    } 
}

private void tabControl1_MouseClick(object sender, MouseEventArgs e)
{

}

private void textBox3_MouseClick(object sender, MouseEventArgs e)
{
    setMouseEntered(true);

}

private void label3_Click(object sender, EventArgs e)
{

}

private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
{
    getReadingPace();
}

private void comboBox3_SelectedIndexChanged(object sender, EventArgs e)
{

}

private void button1_Click_1(object sender, EventArgs e)
{
    saveText(true);
    Thread.Sleep(800);
    manualForwardText(true);
}

private void tabPage2_Click(object sender, EventArgs e)
{

}

private void textBox1_KeyDown(object sender, KeyEventArgs e)
{
    setMouseEntered(true);
    if (e.KeyCode == Keys.F1)
    {
        Console.WriteLine("F1 PRESSED");
       // doubleClickText1NoMark();
    }
    else if (e.KeyCode == Keys.ControlKey)
    {
        manualForwardText(true);
    }
    else if (e.KeyCode == Keys.F8)
    {
        manualForwardText(false);
    }
    else
    {
        Console.WriteLine("Nothing PRESSED");
    }
}

private void button2_Click_1(object sender, EventArgs e)
{
    manualForwardText(false);
}

private void tabPage3_Click(object sender, EventArgs e)
{

}

private void textBox4_MouseClick(object sender, MouseEventArgs e)
{
    setMouseEntered(true);
}

private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
{
    setMouseEntered(true);
   
}

private void textBox4_Click(object sender, EventArgs e)
{
    setMouseEntered(true);
}

private void textBox4_MouseDown(object sender, MouseEventArgs e)
{
    setMouseEntered(true);
}


    }
}
