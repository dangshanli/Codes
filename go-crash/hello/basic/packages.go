package basic

import (
	"fmt"
	"math"
	"math/rand"
)

/*
 * package 是组成go 程序的单位，也是import 时候的基本单位
 */
func FavoriteNum() {
	fmt.Println("my favorite number is ", rand.Intn(10))
}

/*
 *基本的导包操作
 */
func KindOfBasic() {
	//导出math
	fmt.Printf("now you have %g problems.\n", math.Sqrt(7))
	//
	fmt.Println(math.Pi)

	fmt.Printf("33+44=%v\n", add(33, 44))
	fmt.Println(swap("hello", "world"))
	fmt.Println(split(33))
	decVar()
}

func add(x int, y int) int {
	return x + y
}

func swap(x, y string) (string, string) {
	return y, x
}

func split(sum int) (x, y int) {
	x = sum * 4 / 9
	y = sum - x
	return
}

var c, python, java bool

func decVar() {
	var i int
	fmt.Println(i, c, python, java)
}












