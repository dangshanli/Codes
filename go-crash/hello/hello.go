package main

import (
	"fmt"
	"log"

	"rsc.io/quote"

	// "example/hello/basic"

	"example.com/greetings"

	// "example/hello/methodsinterfaces"
	"example/hello/moretype"
)

func main() {
	//first()
	// basic.FavoriteNum()
	// basic.KindOfBasic()
	// basic.Short()
	// basic.Sum1()
	// basic.Sum2()

	// for i := 1; i < 11; i++ {
	// 	fmt.Println("计算", i, "的二次根：")
	// 	result := basic.NewtonSqrt(float64(i))
	// 	fmt.Println("result0=",result,",\nresult1=",math.Sqrt(float64(i)))
	// 	printDelemiter()
	// }
	// result := basic.NewtonSqrt(float64(10003))
	// 	fmt.Println("result0=",result,",\nresult1=",math.Sqrt(float64(10003)))

	// basic.TimeToSunday()
	// basic.OtherSwitch()
	// basic.HelloDefer()

	// v := methodsinterfaces.Vertex{3,4}
	// fmt.Println(v.Abs())
	// methodsinterfaces.Me()

	moretype.Pp()
	moretype.PrintOneVertex()
	fmt.Println("bibi=",moretype.Bibi())
	fmt.Println("Dodo=",moretype.Dodo)	
}

func first() {
	fmt.Println("Hello,Go")
	fmt.Println(quote.Go())

	printDelemiter()

	log.SetPrefix("greetings:")
	log.SetFlags(0)

	message, err := greetings.Hello("Tom")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(message)

	printDelemiter()

	//列表有序 map默认乱序
	names := []string{"Tom", "Alice", "Lily"}
	names = append(names, "Bibi", "MiaoMiao")

	messages, errMulti := greetings.Hellos(names)
	if errMulti != nil {
		log.Fatal(errMulti)
	}
	fmt.Println(messages)
}

func printDelemiter() {
	fmt.Println("-----------------------------")
}


