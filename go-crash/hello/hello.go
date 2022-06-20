package main

import (
	"fmt"
	"log"

	"rsc.io/quote"

	"example.com/greetings"
)

func main() {
	fmt.Println("Hello,Go")
	fmt.Println(quote.Go())

	log.SetPrefix("greetings:")
	log.SetFlags(0)

	message, err := greetings.Hello("Tom")
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println(message)
}
