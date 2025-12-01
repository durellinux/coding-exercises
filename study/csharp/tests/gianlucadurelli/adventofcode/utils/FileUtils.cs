using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;

namespace tests.gianlucadurelli.adventofcode.utils;

public class FileUtils
{
    public static string ReadFile(string filePath)
    {
        return File.ReadAllText(filePath);
    }

    public static string ReadAocInput(int year, string fileName)
    {
        string filePath = "./gianlucadurelli/adventofcode/year" + year + "/resources/"+ fileName +".txt";
        return ReadFile(filePath);
    }

    public static IList<string> ReadAocInputLines(int year, string fileName)
    {
        string content = ReadAocInput(year, fileName);
        return content.Split("\n").ToList();
    }
}