declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define void @foo()
{
entry:
%.t1 = add i32 2, 2
call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 %.t1)
%.t2 = add i1 0,0
br label %exit
exit:
ret void
}

define i32 @main ()
{
call void @foo ()
ret i32 0
}
