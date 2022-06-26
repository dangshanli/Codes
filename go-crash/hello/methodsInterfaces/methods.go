package methodsinterfaces

import (
	"example/hello/myutil"
	"example/hello/other"
	"fmt"
	"math"
	"math/rand"
)

type Vertex struct {
	X, Y float64
}

//将函数挂载结构体值上，无法修改原结构体的值
func (v Vertex) Abs() float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

//将方法挂载到指针结构体上，可以修改接受的内部变量
func (v *Vertex) Scale(scale float64) {
	v.X = v.X * scale
	v.Y = v.Y * scale
}

func Me() {
	fmt.Println("tell me")
	f := MyFloat(-3.456)
	fmt.Println(f.nbs())

	v := Vertex{3, 4}
	v.Scale(10)
	fmt.Println(v.Abs())

	useInterface()

	otherInterface()
}

type MyFloat float64

func (f MyFloat) nbs() float64 {
	myutil.PrintHeader("函数挂载到非结构体的type上")
	if f < 0 {
		return float64(-f)
	}
	return float64(f)
}

type Abser interface {
	Abs() float64
}

func useInterface() {
	myutil.PrintHeader("使用接口")
	var a Abser = Vertex{3, 4}
	fmt.Println(a.Abs())
}

type T struct {
	S string
}

func (t T) M() {
	fmt.Println("invoke method M by", t.S)
}

func (t T) B() int {
	rand.Seed(8964)
	return rand.Intn(778)
}

func otherInterface() {
	myutil.PrintHeader("接口定义在其他包中")
	var a other.I
	t := T{"rabbit"}
	a = t
	a.M()
	fmt.Println(a.B())
}

type Momo interface {
	M()
}
