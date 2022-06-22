package moretype

import "fmt"

type Vertex struct {
	X, Y int
}

func PrintOneVertex() {
	v := Vertex{1, 2}
	fmt.Println(v)
	fmt.Println("v.X=",v.X)
	fmt.Println("v.Y=",v.Y)
}

var bibi = 42;

var Dodo = 88.99

func Bibi() int{
	return bibi;
}