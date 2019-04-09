declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @add( i32 %.a)
{
entry:
%.p.return = alloca i32
%.p.a = alloca i32
store i32 %.a, i32* %.p.a
%.t1 = load i32, i32* %.p.a
%.t3 = load i32, i32* %.p.a
%.t2 = add i32 %.t3, 1
store i32 %.t2, i32* %.p.a
%.t4 = add i1 0,0
br label %exit
exit:
%.t5 = load i32, i32* %.p.return
ret i32 %.t5
}

define i32 @main ()
{
%.p.y = alloca i32
%.t6 = call i32 @add (i32 1)
store i32 %.t6, i32* %.p.y
%.t7 = load i32, i32* %.p.y
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t7)
ret i32 0
}
