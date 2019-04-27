declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define i32 @square( i32 %.n)
{
entry:
%.p.return = alloca i32
%.p.n = alloca i32
store i32 %.n, i32* %.p.n
%.t2 = load i32, i32* %.p.n
%.t3 = load i32, i32* %.p.n
%.t1 = mul i32 %.t2, %.t3
store i32 %.t1, i32* %.p.return
%.t4 = add i1 0,0
br label %exit
exit:
%.t5 = load i32, i32* %.p.return
ret i32 %.t5
}

define i32 @main ()
{
%.t6 = call i32 @square (i32 2)
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t6)
ret i32 0
}
