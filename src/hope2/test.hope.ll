declare i32 @printf(i8*, ...) #1
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
define i32 @main ()
{
%.p.var = alloca i32
%.t1 = sub i32 5, 2
store i32 %.t1, i32* %.p.var
%.t3 = load i32, i32* %.p.var
%.t2 = add i32 %.t3, 3
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t2)
ret i32 0
}
