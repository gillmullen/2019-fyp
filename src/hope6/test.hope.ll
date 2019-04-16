declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @fibonacci( i32 %.N)
{
entry:
%.p.return = alloca i32
%.p.N = alloca i32
store i32 %.N, i32* %.p.N
%.p.num1 = alloca i32
%.p.num2 = alloca i32
%.p.fib = alloca i32
%.p.i = alloca i32
store i32 0, i32* %.p.num1
store i32 1, i32* %.p.num2
store i32 0, i32* %.p.i
br label %label1
label1: %.t1 = load i32, i32* %.p.i
%.t2 = load i32, i32* %.p.N
%.t3 = icmp slt i32 %.t1, %.t2
br i1 %.t3, label %label2, label %label3
label2:
%.t5 = load i32, i32* %.p.num1
%.t6 = load i32, i32* %.p.num2
%.t4 = add i32 %.t5, %.t6
store i32 %.t4, i32* %.p.fib
%.t7 = load i32, i32* %.p.num2
store i32 %.t7, i32* %.p.num1
%.t8 = load i32, i32* %.p.fib
store i32 %.t8, i32* %.p.num2
%.t10 = load i32, i32* %.p.i
%.t9 = add i32 %.t10, 1
store i32 %.t9, i32* %.p.i
br label %label1
label3:
%.t11 = load i32, i32* %.p.fib
store i32 %.t11, i32* %.p.return

%.t12 = add i1 0,0
br label %exit
exit:
%.t13 = load i32, i32* %.p.return
ret i32 %.t13
}

define i32 @main ()
{
%.p.f = alloca i32
%.t14 = call i32 @fibonacci (i32 7)
store i32 %.t14, i32* %.p.f
%.t15 = load i32, i32* %.p.f
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t15)
ret i32 0
}
