package main

import (
	"fmt"
	"log"

	"rsc.io/quote"

	"example.com/greetings"
)

func main() {
	//first()

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
