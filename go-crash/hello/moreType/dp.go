package moretype

import (
	"fmt"
	"log"
	"os"
	"strings"
)

func ResoveDp() {
	content, err := os.ReadFile("E:\\Codes\\Codes\\go-crash\\resource\\dp.txt")
	if err != nil {
		log.Fatal(err)
	}
	// fmt.Println(string(content))

	s := strings.Split(string(content), "\n")
	fmt.Println(len(s))
	fmt.Println(s[3])
	fmt.Println(s[1])
	fmt.Println(s[2])
	for i := 0; i < len(s); i++ {
		if strings.Contains(s[i], "连接失败") {
			fmt.Printf("i=%d,v=%v\n", i, s[i])
		}
	}
}
