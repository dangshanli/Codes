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

	// invokePic()

	makeMap()

	assignMap()

	modifyMap()

	aWc()

	functionAsParam()

	twoAdder()

	doFib()
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

type Vertex2 struct {
	Lat, Long float64
}

func makeMap() {
	myutil.PrintHeader("map的创建和赋值")
	m := make(map[string]Vertex2)
	fmt.Println(m)
	m["Bell Lab"] = Vertex2{45.66, -78.998}
	fmt.Println(m["Bell Lab"])
}

func assignMap() {
	myutil.PrintHeader("初始化Map")
	var m = map[string]Vertex2{
		"Bell":   {33.9, 90.0},
		"Google": {34.009, -54.99},
	}
	fmt.Println(m)
}

func modifyMap() {
	myutil.PrintHeader("修改map")
	m := make(map[string]int)
	key := "answer"
	m[key] = 44
	fmt.Println("value=", m["answer"])

	m[key] = 99
	fmt.Println("after modifying,value=", m["answer"])

	delete(m, key)

	v, ok := m[key]
	fmt.Printf("value=%v,ok?=%v\n", v, ok)

}

func aWc() {
	myutil.PrintHeader("Word Counter")
	m := countWord("foo bar ss 3 dd m m over over like foo bar")
	fmt.Println(m)
}

func countWord(s string) map[string]int {
	result := make(map[string]int) //返回值
	words := strings.Fields(s)     //分割字符串
	for _, v := range words {
		elm, ok := result[v]
		if ok {
			result[v] = (elm + 1)
		} else {
			result[v] = 1
		}
	}
	return result
}

//函数作为参数
//函数作为变量定义在 函数内部，相当于函数中的函数
func compute(fn func(float64, float64) float64) float64 {
	return fn(3, 4)
}

func functionAsParam() {
	myutil.PrintHeader("函数内部定义函数变量，函数作为入参")
	//定义一个内部函数，函数作为变量，可被传递做入参，也可以作为返回值
	hypot := func(x, y float64) float64 {
		return math.Sqrt(x*x + y*y)
	}

	fmt.Println(hypot(5, 12))      //算出 hypot->5,12的值
	fmt.Println(compute(hypot))    //hypot(3,4)
	fmt.Println(compute(math.Pow)) //math.Pow(3,4)
}

func adder() func(int) int {
	sum := 0
	return func(i int) int {
		sum += i
		return sum
	}
}

func twoAdder() {
	myutil.PrintHeader("函数作为返回值")
	pos, neg := adder(), adder()
	for i := 0; i < 10; i++ {
		fmt.Println(
			pos(i),
			neg(-2*i),
		)
	}
}

//0 1 1 2 3 5
func fibonacci() func() int {
	current, last, times := 1, 0, 0
	return func() int {
		if times == 0 {
			times++
			return 0
		}

		if times == 1 {
			times++
			return 1
		}

		next := current + last
		last = current
		current = next
		return next
	}
}

func doFib() {
	myutil.PrintHeader("使用递归斐波那契数列")
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}

func printSlice(s []string) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

func printSlice2(a []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(a), cap(a), a)
}
