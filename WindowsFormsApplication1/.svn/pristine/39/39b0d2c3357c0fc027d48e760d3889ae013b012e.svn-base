using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.IO;


namespace WindowsFormsApplication1.util
{
    class Form1Util
    {
        /// <summary>
        /// Executes a shell command synchronously.
        /// </summary>
        /// <param name="command">string command</param>
        /// <returns>string, as output of the command.</returns>
        public static void ExecuteCommandSync(object command)
        {
            try
            {
                // create the ProcessStartInfo using "cmd" as the program to be run,
                // and "/c " as the parameters.
                // Incidentally, /c tells cmd that we want it to execute the command that follows,
                // and then exit.
                System.Diagnostics.ProcessStartInfo procStartInfo =
                new System.Diagnostics.ProcessStartInfo("cmd", "/c " + command);

                // The following commands are needed to redirect the standard output.
                // This means that it will be redirected to the Process.StandardOutput StreamReader.
                procStartInfo.RedirectStandardOutput = true;
                procStartInfo.UseShellExecute = false;
                // Do not create the black window.
                procStartInfo.CreateNoWindow = true;
                // Now we create a process, assign its ProcessStartInfo and start it
                System.Diagnostics.Process proc = new System.Diagnostics.Process();
                proc.StartInfo = procStartInfo;
                proc.Start();
                // Get the output into a string
              //  string result = proc.StandardOutput.ReadToEnd();
                String result = "";
                while ((result = proc.StandardOutput.ReadLine()) != null)
                {
                    Console.WriteLine(result);
                }

                // Display the command output.
               // Console.WriteLine(result);
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);

                // Log the exception
            }
        }

        /// <summary>
        /// Execute the command Asynchronously.
        /// </summary>
        /// <param name="command">string command.</param>
        public static void ExecuteCommandAsync(string command)
        {
            Console.WriteLine("Command: " + command);
            try
            {
                //Asynchronously start the Thread to process the Execute command request.
                Thread objThread = new Thread(new ParameterizedThreadStart(ExecuteCommandSync));
                //Make the thread as background thread.
                objThread.IsBackground = true;
                //Set the Priority of the thread.
                objThread.Priority = ThreadPriority.BelowNormal;
                //Start the thread.
                objThread.Start(command);
            }
            catch (ThreadStartException e)
            {
                Console.WriteLine("Exception: " + e.Message);
                // Log the exception
            }
            catch (ThreadAbortException e)
            {
                Console.WriteLine("Exception: " + e.Message);
                // Log the exception
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception: " + e.Message);
                // Log the exception
            }
        }


    }
}
