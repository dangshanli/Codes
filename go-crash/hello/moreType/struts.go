package moretype

import "fmt"

type Vertex struct {
	X, Y int
}

func PrintOneVertex() {
	v := Vertex{1, 2}
	fmt.Println(v)
	fmt.Println("v.X=", v.X)
	fmt.Println("v.Y=", v.Y)
}

var bibi = 42

var Dodo = 88.99

func Bibi() int {
	return bibi
}

func PointerRef() {
	v := Vertex{1, 2}
	fmt.Println(v)
	fmt.Println("-----------------------------")
	vb := changeVertex(v)
	fmt.Println(v)
	fmt.Println(vb)
	fmt.Println("-----------------------------")
	changeByPointer(&v)
	fmt.Println(v)
	fmt.Println("-----------------------------")
	fmt.Println(vv)
	changeMember()
	fmt.Println(vv)

}

var vv = Vertex{4, 4}

func changeVertex(vertext Vertex) Vertex {
	vertext.X = vertext.X * 2
	vertext.Y = vertext.Y * 2
	return vertext
}

func changeMember() {
	vv.X *= 2
	vv.Y *= 3

}

func changeByPointer(vx *Vertex) {
	vx.X = vx.X * 2
	vx.Y = vx.Y * 2
}
