import React, { useEffect, useState } from 'react'

export const Rating = (props) => {

    const [rate, setRate] = useState(0);

    const rating = (number) => {
        setRate(number);
        props.setUserRate(number);
    }

    useEffect(() => {
        console.log(rate);
    }, [rate]);

    if (props.readOnly) {
        return (
            <div className='d-flex justify-content-center'>
                <ul className="d-flex gap-3 me-4">
                    {Array.from({ length: 5 }, (_, index) => index).map((_, index) => {
                        if (index < props.rating)
                            return (
                                <li key={index} className='list-group-item'>
                                    <i className="far fa-star fa-sm text-primary fa-solid"></i>
                                </li>
                            )
                        else
                            return (
                                <li key={index} className='list-group-item'>
                                    <i className="far fa-star fa-sm text-primary"></i>
                                </li>
                            )
                    })
                    }
                </ul>
            </div>
        )
    } else {
        return (
            <div className='d-flex justify-content-center'>
                <ul className="d-flex gap-3 me-4">
                    {Array.from({ length: 5 }, (_, index) => index).map((_, index) => {
                        if (index < rate)
                            return (
                                <li key={index} className='list-group-item' onClick={() => rating(index + 1)}>
                                    <i className="far fa-star fa-sm text-primary fa-solid"></i>
                                </li>
                            )
                        else
                            return (
                                <li key={index} className='list-group-item' onClick={() => rating(index + 1)}>
                                    <i className="far fa-star fa-sm text-primary"></i>
                                </li>
                            )
                    })
                    }
                </ul>
            </div>
        )
    }
}
