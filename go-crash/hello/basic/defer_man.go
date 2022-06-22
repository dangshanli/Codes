package basic

import "fmt"

func HelloDefer() {
	defer fmt.Println(give("go"))
	fmt.Println("你好")
}

func give(name string) string {
	fmt.Println("invoke give func")
	return name + " dodo"
}
