using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace WindowsFormsApplication1
{
    class FileProcessing
    {
        public static Boolean renameFile(String file1, String file2)
        {
            if(File.Exists(file1)) {
                File.Move(file1,file2);
            } else {
                return false;
            }

            return true;
        }

        public static String fileToString(String FILE_NAME) 
        {
            StringBuilder rSBuilder = new StringBuilder();
            if (!File.Exists(FILE_NAME))
            {
                Console.Write(".");
                return "";
            }
            try
            {
                using (StreamReader sr = File.OpenText(FILE_NAME))
                {
                    String input;
                    while ((input = sr.ReadLine()) != null)
                    {
                        rSBuilder.AppendLine(input);
                        // Console.WriteLine(input);
                    }
             //       rSBuilder.Remove(rSBuilder.Length - 2, 2);

                //    Console.WriteLine("The end of the stream has been reached.");
                }
            }
            catch (Exception e) {Console.WriteLine("Error: "+e.Message); }


            return rSBuilder.ToString().TrimEnd('\r', '\n');
        }

        public static String fileToStringFilter(String FILE_NAME)
        {
            StringBuilder rSBuilder = new StringBuilder();
            if (!File.Exists(FILE_NAME))
            {
                Console.Write(".");
                return "";
            }
            try
            {
                using (StreamReader sr = File.OpenText(FILE_NAME))
                {
                    String input;
                    while ((input = sr.ReadLine()) != null)
                    {
                        rSBuilder.AppendLine(input);
                        // Console.WriteLine(input);
                    }
                  //  rSBuilder.Remove(rSBuilder.Length - 2, 2);
                    //    Console.WriteLine("The end of the stream has been reached.");
                }
            }
            catch (Exception e) { Console.WriteLine("Error: " + e.Message); }


            return WindowsFormsApplication1.util.StringProcessing.bufFilter_StringBuilder(rSBuilder).ToString();
        }

        public static String getFilePath(String curdirfile)
        {
            String curdir = curdirfile.Substring(0, curdirfile.IndexOf(getFileName(curdirfile)));
            if (curdir == null) return "";
            return curdir;
        }

        public static String getFileName(String curdirfile_in)
        {
            String separator = "/";
            String curdirfile = curdirfile_in;
            if (curdirfile_in.EndsWith(separator))
            {
                curdirfile = curdirfile_in.Substring(0, curdirfile_in.LastIndexOf(separator));
            }
            StringBuilder curdirfileBuff = new StringBuilder(curdirfile);
//            StringBuffer curdirfileBuff = new StringBuffer(curdirfile);

            if (curdirfile.LastIndexOf(separator) >= 0)
            {
                if (!curdirfile.EndsWith(separator))
                {
                    curdirfileBuff.Remove(0, curdirfile.LastIndexOf(separator) + 1);
                }
            }
            return curdirfileBuff.ToString();
        }

        public static String getFileNameWithOutExtension(String filename_in)
        {
            if (filename_in == null) return "";
            String filename_out = filename_in;
            if (filename_in.LastIndexOf('.') > 0)
            {
                filename_out = filename_in.Substring(0, filename_in.LastIndexOf('.'));
            }
            return filename_out;
        }

        public static void copyStringToFile(String args1, String outFile)
        {
        //    if (args1.Length < 1) return;

            Directory.CreateDirectory(getFilePath(outFile));
                
            /*        StringBuilder sBuilder = new StringBuilder();
              using (StreamReader sr = new StreamReader(args1))
                 {
                     sBuilder.AppendLine(sr.ReadToEnd());
                 }
             */
            using (StreamWriter outfile2 = new StreamWriter(outFile))
            {
                outfile2.Write(args1);
            }
        } // end procedure

        public static void appendStringToFile(String args1, String outFile)
        {
            if (args1.Length < 1) return;
            Directory.CreateDirectory(getFilePath(outFile));
            if (!File.Exists(outFile))
            {
                using (StreamWriter sw = File.CreateText(outFile))
                {
                    sw.Write(args1);
                }
            }
            else
            {
                using (StreamWriter sw = File.AppendText(outFile))
                {
                    sw.Write(args1);
                }
            }

        } // end procedure

        public static String getOrigTXTFileFromWav(String wavFileName_in)
        {
            String txtFile = getFileNameWithOutExtension(wavFileName_in) + ".txt";
            return txtFile;
        }
        
        public static String getInsTXTFileFromWav(String wavFileName_in)
        {
            String txtFile = getFileNameWithOutExtension(wavFileName_in) + "/InsTime.txt";
            return txtFile;
        }
        public static String getTXTFileFromWav(String wavFileName_in)
        {
            String txtFile = getFileNameWithOutExtension(wavFileName_in) + ".txt";
            return txtFile;
        }
        public static String getTXTProcessingFileFromWav(String wavFileName_in)
        {
            String txtFile = getFileNameWithOutExtension(wavFileName_in) + "_processing.txt";
            return txtFile;
        }
        public static String getWavFileFromMp3(String mp3FileName_in)
        {
            String txtFile = getFileNameWithOutExtension(mp3FileName_in) + ".wav";
            return txtFile;
        }

        public static String getFinalTXTFileFromWav(String wavFileName_in)
        {
            String txtFile = getFilePath(wavFileName_in) + "/" + getFileNameWithOutExtension(getFileName(wavFileName_in)) + ".lrc";
          //  String txtFile = getFilePath(wavFileName_in) + "/out2/" + getFileNameWithOutExtension(getFileName(wavFileName_in))+".lrc";
            return txtFile;
        }

    } // end class
}