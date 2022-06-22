package basic

import (
	"fmt"
	"time"
)

func TimeToSunday() {
	fmt.Println("啥时候星期六?")
	today := time.Now().Weekday()
	switch time.Saturday {
	case today + 0:
		fmt.Println("今天")
	case today + 1:
		fmt.Println("明天")
	case today + 2:
		fmt.Println("后天")
	default:
		fmt.Println("早呢，洗洗睡吧")
	}
}

func OtherSwitch() {
	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("上午")
	case t.Hour() < 17:
		fmt.Println("下午")
	default:
		fmt.Println("晚上")
	}
}
