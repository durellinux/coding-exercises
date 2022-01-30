using NUnit.Framework;
using src.giuliarumi;

namespace tests.giuliarumi
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void Test1()
        {
            Class1 class1 = new Class1();
            Assert.True(class1.val == "val");
        }
    }
}
