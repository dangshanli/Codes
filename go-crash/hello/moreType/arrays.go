package moretype

import (
	"example/hello/myutil"
	"fmt"
	"math"
	"strings"

	"golang.org/x/tour/pic"
)

func SomeArray1() {
	var a [2]string
	a[0] = "dodo"
	a[1] = "jojo"
	fmt.Println(a[0], a[1])
	fmt.Println(a)

	primes := [6]int{2, 3, 5, 7, 11, 19}
	fmt.Println(primes)

	var s []int = primes[1:4]
	fmt.Println(s)

	changeSlice()

	sliceAboveArray()

	sliceAction()

	sliceOfLenAndCap()

	makeSlice()

	sliceOfSlice()

	appendSlice()

	rangeUse()

	rangeSkip()

	invokePic()
}

/**
 *
 * 改切片以影响底层数组
 */
func changeSlice() {
	myutil.PrintHeader("修改slice级联底层数组")
	names := [4]string{"Tom", "Alice", "a", "Kong"}

	fmt.Println(names)

	a := names[0:2]
	b := names[1:3]
	fmt.Println(a, b)

	b[0] = "XXXXXXX"
	fmt.Println(a, b)
	fmt.Println(names)
}

/**
 * 结构体定义
 */
type Dojo struct {
	i int
	bool
}

/**
 * 初始化slice
 */
func sliceAboveArray() {
	myutil.PrintHeader("slice 直接初始化演示")
	q := []int{2, 3, 5, 7, 88}
	fmt.Println(q)

	r := []bool{true, false, true}
	fmt.Println(r)

	m := []struct {
		j string
		k bool
	}{
		{"Alice", true},
		{"Vangof", false},
		{"Picasso", true},
	}
	fmt.Println(m)

	s := []Dojo{
		{1, true},
		{3, false},
		{5, true},
		{7, false},
		{9, true},
	}
	fmt.Println(s)
}

/**
 * slice 取值feature
 */
func sliceAction() {
	myutil.PrintHeader("slice 取值 feature")
	langs := []string{
		"Java",
		"Python",
		"English",
		"JS",
		"Go",
		"C++",
		"SQL",
		"Julia",
		"Lisp",
	}
	fmt.Println("langs 长度：", len(langs))
	fmt.Println("[:]全局打印：", langs[:])
	fmt.Println("[3:],JS以后的全元素：", langs[3:])
	fmt.Println("[:7]，取值直到SQL：", langs[:7])
	fmt.Println("[3:5]：", langs[3:5])
}

/**
 * 切片的长度和容量
 */
func sliceOfLenAndCap() {
	myutil.PrintHeader("切片的长度和容量")
	s := []string{"hell", "walker", "car", "rest", "micro"}
	printSlice(s[:])
	printSlice(s[:3])
	printSlice(s[2:])
	printSlice(s[1:4])
}

/**
 * make 创建 slice
 */
func makeSlice() {
	myutil.PrintHeader("使用make创建slice")
	s := make([]int, 5)
	printSlice2(s)
	s = s[1:3]
	printSlice2(s)
	s = s[1:]
	printSlice2(s)
	s = s[:cap(s)]
	printSlice2(s)

}

//切片的元素为切片
func sliceOfSlice() {
	myutil.PrintHeader("切片的切片,一个井字游戏")
	//创建切片3X3
	board := [][]string{
		{"_", "_", "_"},
		{"_", "_", "_"},
		{"_", "_", "_"},
	}
	board[0][0] = "X"
	board[2][2] = "O"
	board[1][2] = "X"
	board[1][0] = "O"
	board[0][2] = "X"

	for i := 0; i < len(board); i++ {
		fmt.Printf("%s\n", strings.Join(board[i], " "))
	}
}

//追加切片
func appendSlice() {
	myutil.PrintHeader("切片追加元素")
	var s []int
	printSlice2(s)

	s = append(s, 1, 3, 5, 7, 9)
	printSlice2(s)
}

func rangeUse() {
	myutil.PrintHeader("range slice")
	var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}
}

func rangeSkip() {
	myutil.PrintHeader("range slice skip")
	pow := make([]int, 10)
	for i := range pow {
		pow[i] = 1 << uint(i) // 2**i
	}
	for _, v := range pow {
		fmt.Printf("%d\n", v)
	}
}

func aPic(dx, dy int) [][]uint8 {
	s := make([][]uint8, dy)
	for i := range s {
		s[i] = make([]uint8, dx)
	}

	for i := 0; i < len(s); i++ {
		for j := range s[i] {
			//(x+y)/2, x*y, x^y, x*log(y) 和 x%(y+1) 几个公式
			s[i][j] = uint8(i * int(math.Log(float64(j))))
		}
	}
	return s
}

func invokePic() {
	myutil.PrintHeader("pic show，这里会转换出一张图，终端只会显示出相应的字符串")
	pic.Show(aPic)
}

func printSlice(s []string) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

func printSlice2(a []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(a), cap(a), a)
}
