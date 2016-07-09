using System;
using System.Net.Http;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace ConsoleApplication
{
    public class Program
    {
        protected static async Task<string> GetWebpageContent(string url)
        {
            using (var client = new HttpClient())
                return 
                    await 
                    client
                    .GetStringAsync(url)
                    .ConfigureAwait(false);
        }

        protected static void ProcessContent(string text)
        {
            if (string.IsNullOrWhiteSpace(text))
            {
                Console.WriteLine("no content to process");
                return;
            }

            var regex = 
                new Regex(
                    "<a.*href\\s?=\\s?['\\\"]([^\\\"\\']*)[\'\\\"][^>]*\\>(.*)<\\/a>");

            foreach(Match match in regex.Matches(text))
            {
                Console.WriteLine(
                    $"{match.Groups[2]} -> {match.Groups[1]}");
            }
        }

        public static void Main(string[] args)
        {
            Task.Run(
                async ()=>
                {
                    ProcessContent(
                        await
                        GetWebpageContent("http://www.spacex.com")
                        .ConfigureAwait(false));
                })
            .Wait();
        }
    }
}
